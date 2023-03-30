package edu.hm.network.group12.api.dto.here;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteDto {

    @JsonProperty(value = "routes")
    private ArrayList<RouteEntryDto> routeEntryDtos;

    public RouteDto() {
    }

    public RouteDto(ArrayList<RouteEntryDto> routeEntryDtos) {
        this.routeEntryDtos = routeEntryDtos;
    }

    public ArrayList<RouteEntryDto> getSectionsDtos() {
        return routeEntryDtos;
    }

    public RouteDto setSectionsDtos(ArrayList<RouteEntryDto> routeEntryDtos) {
        this.routeEntryDtos = routeEntryDtos;
        return this;
    }
}
