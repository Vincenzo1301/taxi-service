package edu.hm.network.group12.api.dto.taxi;

public class TaxiAvailabilityDto {

    private Long taxiNumber;

    private boolean available;

    public Long getTaxiNumber() {
        return taxiNumber;
    }

    public TaxiAvailabilityDto setTaxiNumber(Long taxiNumber) {
        this.taxiNumber = taxiNumber;
        return this;
    }

    public boolean isAvailable() {
        return available;
    }

    public TaxiAvailabilityDto setAvailable(boolean available) {
        this.available = available;
        return this;
    }
}
