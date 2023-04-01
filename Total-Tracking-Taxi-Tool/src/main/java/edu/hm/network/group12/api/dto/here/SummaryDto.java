package edu.hm.network.group12.api.dto.here;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SummaryDto {

    /**
     * Duration in seconds from origin address to destination address.
     */
    private int duration;

    public SummaryDto() {
    }

    public SummaryDto(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public SummaryDto setDuration(int duration) {
        this.duration = duration;
        return this;
    }
}
