package edu.hm.network.group12.service.impl;

import edu.hm.network.group12.api.dto.hue.LightStateDto;
import edu.hm.network.group12.api.dto.taxi.TaxiDriveDto;
import edu.hm.network.group12.api.dto.taxi.TaxiDto;
import edu.hm.network.group12.client.HereWebClient;
import edu.hm.network.group12.client.LampClient;
import edu.hm.network.group12.exception.TaxiNotAvailableException;
import edu.hm.network.group12.exception.TaxiNotFoundException;
import edu.hm.network.group12.persistence.TaxiRepository;
import edu.hm.network.group12.persistence.model.Taxi;
import edu.hm.network.group12.quartz.LampJob;
import edu.hm.network.group12.quartz.TaxiJob;
import edu.hm.network.group12.service.TaxiService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.quartz.SimpleScheduleBuilder.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@Slf4j
public class TaxiServiceImpl implements TaxiService {

    private static final int TIME_PUFFER = 300;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TaxiRepository taxiRepository;

    @Autowired
    private HereWebClient hereWebClient;

    @Autowired
    private LampClient lampClient;

    @Autowired
    private Scheduler scheduler;

    private void createTaxiJob(long taxiNumber, int durationForRoute) throws SchedulerException {
        final Map<String, Long> dataForTaxiJob = new HashMap<>();
        dataForTaxiJob.put("taxiNumber", taxiNumber);

        final JobDetail taxiJob = JobBuilder.newJob(TaxiJob.class)
                .withIdentity("job:taxi:" + taxiNumber)
                .setJobData(new JobDataMap(dataForTaxiJob))
                .build();

        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, durationForRoute);

        final SimpleTrigger taxiTrigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity("trigger:taxi:" + taxiNumber)
                .startAt(calendar.getTime())
                .forJob("job:taxi:" + taxiNumber)
                .build();

        scheduler.scheduleJob(taxiJob, taxiTrigger);
    }

    private void createLampJob(long taxiNumber, int durationForRoute, long inTime) throws SchedulerException {
        final Map<String, Long> dataForLampJob = new HashMap<>();
        dataForLampJob.put("taxiNumber", taxiNumber);
        dataForLampJob.put("inTime", inTime);

        final JobDetail lampJob = JobBuilder.newJob(LampJob.class)
                .withIdentity("job:lamp:" + taxiNumber)
                .setJobData(new JobDataMap(dataForLampJob))
                .build();

        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, durationForRoute);

        final SimpleTrigger lampTrigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger:lamp:" + taxiNumber)
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(5)
                        .repeatForever())
                .endAt(calendar.getTime())
                .forJob("job:lamp:" + taxiNumber)
                .build();

        scheduler.scheduleJob(lampJob, lampTrigger);
    }

    @Transactional
    @Override
    public TaxiDto createTaxi(@NonNull TaxiDto taxiDto) {
        final Taxi taxi = new Taxi();
        taxi.setTaxiNumber(taxiDto.getTaxiNumber());
        taxi.setDriverFirstName(taxiDto.getDriverFirstName());
        taxi.setDriverLastName(taxiDto.getDriverLastName());
        taxi.setAvailable(false);
        taxiRepository.save(taxi);

        lampClient.turnLightOnOrOff(taxi.getTaxiNumber(), false);

        log.info("New taxi has been created.");

        return modelMapper.map(taxi, TaxiDto.class);
    }

    @Transactional
    @Override
    public TaxiDriveDto createTaxiDrive(@NonNull TaxiDriveDto taxiDriveDto) {
        final Taxi taxi = taxiRepository.findById(taxiDriveDto.getTaxiNumber()).orElseThrow(() -> new TaxiNotFoundException("The taxi with the number " + taxiDriveDto.getTaxiNumber() + " was not found in the database."));

        if (taxi.isAvailable()) {
            final String origin = taxiDriveDto.getOrigin();
            final String destination = taxiDriveDto.getDestination();
            final int durationForRoute = hereWebClient.getDurationForRoute(origin, destination) + TIME_PUFFER;

            try {
                // Create jobs.
                createTaxiJob(taxiDriveDto.getTaxiNumber(), durationForRoute);
                createLampJob(taxiDriveDto.getTaxiNumber(), durationForRoute, 1L);

                // Update taxi values in the database.
                taxi.setOrigin(origin);
                taxi.setDestination(destination);
                taxi.setDuration(durationForRoute);
                taxi.setAvailable(false);
                taxiRepository.save(taxi);

                // Log information.
                final int hours = durationForRoute / 3600;
                final int minutes = (durationForRoute % 3600) / 60;
                final int seconds = durationForRoute % 60;
                log.info("Taxi " + taxi.getTaxiNumber() + " is now on a " + format("%02d:%02d:%02d", hours, minutes, seconds) + " trip.");
            } catch (SchedulerException e) {
                e.printStackTrace();
            }

            return new TaxiDriveDto(taxi.getTaxiNumber(), taxi.getOrigin(), taxi.getDestination(), taxi.getDuration());
        } else {
            log.info("Taxi " + taxi.getTaxiNumber() + " is not available for a new drive.");
            throw new TaxiNotAvailableException("The taxi " + taxi.getTaxiNumber() + " is not available right now.");
        }
    }

    @Override
    public List<TaxiDto> readAllTaxis() {
        final List<Taxi> allTaxis = taxiRepository.findAll();

        return allTaxis.stream().map(taxi -> modelMapper.map(taxi, TaxiDto.class)).collect(Collectors.toList());
    }

    @Override
    public void updateTaxiAvailability(long taxiNumber, boolean available) {
        final Taxi taxi = taxiRepository.findById(taxiNumber).orElseThrow(() -> new TaxiNotFoundException("The taxi with the number " + taxiNumber + " was not found in the database."));
        taxi.setAvailable(available);
        taxiRepository.save(taxi);

        if (taxi.isAvailable()) {
            lampClient.turnLightOnOrOff(taxiNumber, true);
            lampClient.setLightState(taxiNumber, new LightStateDto.LightStateDtoBuilder().hue(25550).build());
        } else {
            lampClient.turnLightOnOrOff(taxiNumber, false);
        }
    }

    @Override
    public void updateTaxiDrive(TaxiDriveDto taxiDriveDto) {
        final Taxi taxi = taxiRepository.findById(taxiDriveDto.getTaxiNumber()).orElseThrow(() -> new TaxiNotFoundException("The taxi with the number " + taxiDriveDto.getTaxiNumber() + " was not found in the database."));

        try {
            if (scheduler.checkExists(new JobKey("job:taxi:" + taxiDriveDto.getTaxiNumber())) && scheduler.checkExists(new JobKey("job:lamp:" + taxiDriveDto.getTaxiNumber()))) {
                scheduler.deleteJob(new JobKey("job:taxi:" + taxiDriveDto.getTaxiNumber()));
                scheduler.deleteJob(new JobKey("job:lamp:" + taxiDriveDto.getTaxiNumber()));

                final String origin = taxiDriveDto.getOrigin();
                final String destination = taxiDriveDto.getDestination();
                final int durationForRoute = hereWebClient.getDurationForRoute(origin, destination) + TIME_PUFFER;

                createTaxiJob(taxiDriveDto.getTaxiNumber(), durationForRoute);
                if (durationForRoute < taxi.getDuration()) {
                    createLampJob(taxiDriveDto.getTaxiNumber(), durationForRoute, 1L);
                } else {
                    createLampJob(taxiDriveDto.getTaxiNumber(), durationForRoute, 0L);
                }

                // Update taxi values in the database.
                taxi.setOrigin(origin);
                taxi.setDestination(destination);
                taxi.setDuration(durationForRoute);
                taxi.setAvailable(false);
                taxiRepository.save(taxi);

                // Log information
                final int hours = durationForRoute / 3600;
                final int minutes = (durationForRoute % 3600) / 60;
                final int seconds = durationForRoute % 60;
                log.info("Taxi " + taxi.getTaxiNumber() + " is now on a updated " + format("%02d:%02d:%02d", hours, minutes, seconds) + " trip.");
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTaxiDrive(long taxiNumber) {
        final Taxi taxi = taxiRepository.findById(taxiNumber).orElseThrow(() -> new TaxiNotFoundException("The taxi with the number " + taxiNumber + " was not found in the database."));

        try {
            if (scheduler.checkExists(new JobKey("job:taxi:" + taxiNumber)) && scheduler.checkExists(new JobKey("job:lamp:" + taxiNumber))) {
                scheduler.deleteJob(new JobKey("job:taxi:" + taxiNumber));
                scheduler.deleteJob(new JobKey("job:lamp:" + taxiNumber));
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        taxi.setAvailable(true);
        taxi.setDuration(0);
        taxi.setOrigin("");
        taxi.setDestination("");
        taxiRepository.save(taxi);

        lampClient.setLightState(taxiNumber, new LightStateDto.LightStateDtoBuilder().hue(25550).build());
        log.info("Taxi " + taxi.getTaxiNumber() + " stopped and is now available for a new drive.");
    }
}
