package edu.hm.network.group12.api.dto.here;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoCodeDto {

    @JsonProperty(value = "items")
    private ArrayList<GeoCodeItemDto>  geoCodeItems;

    public GeoCodeDto() {

    }

    public GeoCodeDto(ArrayList<GeoCodeItemDto> geoCodeItems) {
        this.geoCodeItems = geoCodeItems;
    }

    public ArrayList<GeoCodeItemDto> getGeoCodeItems() {
        return geoCodeItems;
    }

    public GeoCodeDto setGeoCodeItems(ArrayList<GeoCodeItemDto> geoCodeItems) {
        this.geoCodeItems = geoCodeItems;
        return this;
    }
}
