package edu.hm.network.group12.api.dto.here;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The coordinates (latitude, longitude) of a pin on a map corresponding to the searched place.
 */
public class PositionDto {

    /**
     * Latitude of the address. For example: "52.19404".
     */
    @JsonProperty(value = "lat")
    private String lat;

    /**
     * Longitude of the address. For example: "8.80135".
     */
    @JsonProperty(value = "lng")
    private String lng;

    public PositionDto() {
    }

    public PositionDto(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public PositionDto setLat(String lat) {
        this.lat = lat;
        return this;
    }

    public String getLng() {
        return lng;
    }

    public PositionDto setLng(String lng) {
        this.lng = lng;
        return this;
    }
}
