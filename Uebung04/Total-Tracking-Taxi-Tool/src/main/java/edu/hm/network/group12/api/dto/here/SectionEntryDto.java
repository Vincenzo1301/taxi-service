package edu.hm.network.group12.api.dto.here;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SectionEntryDto {

    @JsonProperty(value = "summary")
    private SummaryDto summaryDto;

    public SectionEntryDto() {
    }

    public SectionEntryDto(SummaryDto summaryDto) {
        this.summaryDto = summaryDto;
    }

    public SummaryDto getSummaryDto() {
        return summaryDto;
    }

    public SectionEntryDto setSummaryDto(SummaryDto summaryDto) {
        this.summaryDto = summaryDto;
        return this;
    }
}
