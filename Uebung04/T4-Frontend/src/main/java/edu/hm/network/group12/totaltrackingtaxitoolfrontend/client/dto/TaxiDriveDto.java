package edu.hm.network.group12.totaltrackingtaxitoolfrontend.client.dto;

/**
 * Data transfer object for state of taxis e.g. estimated travel time.
 */
public class TaxiDriveDto {

    private Long taxiNumber;

    private String origin;

    private String destination;

    private int duration;

    public TaxiDriveDto() {
    }

    public TaxiDriveDto(Long taxiNumber, String origin, String destination, int duration) {
        this.taxiNumber = taxiNumber;
        this.origin = origin;
        this.destination = destination;
        this.duration = duration;
    }

    public Long getTaxiNumber() {
        return taxiNumber;
    }

    public TaxiDriveDto setTaxiNumber(Long taxiNumber) {
        this.taxiNumber = taxiNumber;
        return this;
    }

    public String getOrigin() {
        return origin;
    }

    public TaxiDriveDto setOrigin(String origin) {
        this.origin = origin;
        return this;
    }

    public String getDestination() {
        return destination;
    }

    public TaxiDriveDto setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public int getDuration() {
        return duration;
    }

    public TaxiDriveDto setDuration(int duration) {
        this.duration = duration;
        return this;
    }
}
