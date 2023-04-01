package edu.hm.network.group12.quartz;

import edu.hm.network.group12.api.dto.hue.LightStateDto;
import edu.hm.network.group12.client.LampClient;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
public class LampJob extends QuartzJobBean {

    @Autowired
    private LampClient lampClient;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        final JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        final long taxiNumber = dataMap.getLong("taxiNumber");
        final long inTime = dataMap.getLong("inTime");

        if (inTime == 1) {
            lampClient.setLightState(taxiNumber, new LightStateDto.LightStateDtoBuilder().hue(13000).build());
        } else {
            //lampClient.turnLightOnOrOff(taxiNumber, true);
            lampClient.setLightState(taxiNumber, new LightStateDto.LightStateDtoBuilder().hue(0).alert("lselect").build());
            //lampClient.turnLightOnOrOff(taxiNumber, false);
        }
    }
}
