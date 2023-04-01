package edu.hm.network.group12.api.dto.here;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoCodeItemDto {

    /**
     * The localized display name of this result item.
     */
    @JsonProperty(value = "title")
    private String title;

    /**
     * The unique identifier for the result item. This ID can be used for a Look Up by ID search as well.
     */
    @JsonProperty(value = "id")
    private String id;

    /**
     * The coordinates (latitude, longitude) of a pin on a map corresponding to the searched place.
     */
    @JsonProperty(value = "position")
    private PositionDto positionDto;

    public GeoCodeItemDto() {
    }

    public GeoCodeItemDto(String title, String id, PositionDto positionDto) {
        this.title = title;
        this.id = id;
        this.positionDto = positionDto;
    }

    public String getTitle() {
        return title;
    }

    public GeoCodeItemDto setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getId() {
        return id;
    }

    public GeoCodeItemDto setId(String id) {
        this.id = id;
        return this;
    }

    public PositionDto getPositionDto() {
        return positionDto;
    }

    public GeoCodeItemDto setPositionDto(PositionDto positionDto) {
        this.positionDto = positionDto;
        return this;
    }
}
