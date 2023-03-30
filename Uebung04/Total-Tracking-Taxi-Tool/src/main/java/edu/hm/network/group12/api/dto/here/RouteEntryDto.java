package edu.hm.network.group12.api.dto.here;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteEntryDto {

    @JsonProperty(value = "sections")
    private ArrayList<SectionEntryDto> sectionEntryDtos;

    public RouteEntryDto() {
    }

    public RouteEntryDto(ArrayList<SectionEntryDto> sectionEntryDtos) {
        this.sectionEntryDtos = sectionEntryDtos;
    }

    public ArrayList<SectionEntryDto> getSectionEntryDtos() {
        return sectionEntryDtos;
    }

    public RouteEntryDto setSectionEntryDtos(ArrayList<SectionEntryDto> sectionEntryDtos) {
        this.sectionEntryDtos = sectionEntryDtos;
        return this;
    }
}
