package edu.hm.network.group12.quartz;

import edu.hm.network.group12.api.dto.hue.LightStateDto;
import edu.hm.network.group12.client.LampClient;
import edu.hm.network.group12.exception.TaxiNotFoundException;
import edu.hm.network.group12.persistence.TaxiRepository;
import edu.hm.network.group12.persistence.model.Taxi;
import edu.hm.network.group12.service.TaxiService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * This class is a job for the open source Quartz job scheduling framework.
 * The job is executed when a taxi arrives at the destination after the specified duration.
 */
@Component
@Slf4j
public class TaxiJob extends QuartzJobBean {

    @Autowired
    private TaxiService taxiService;

    @Autowired
    private TaxiRepository repository;

    @Autowired
    private LampClient lampClient;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        final JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        final long taxiNumber = dataMap.getLong("taxiNumber");

        final Taxi taxi = repository.findById(taxiNumber).orElseThrow(() -> new TaxiNotFoundException("The taxi with the number " + taxiNumber + " was not found in the database."));
        taxiService.updateTaxiAvailability(taxiNumber, true);

        log.info("Taxi " + taxi.getTaxiNumber() + " arrived at destination and is now available for a new drive.");
        lampClient.setLightState(taxiNumber, new LightStateDto.LightStateDtoBuilder().hue(25550).build());
    }
}
