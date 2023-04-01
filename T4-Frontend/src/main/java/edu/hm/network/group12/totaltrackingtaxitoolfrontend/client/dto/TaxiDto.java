package edu.hm.network.group12.totaltrackingtaxitoolfrontend.client.dto;

/**
 * Data transfer object for creating taxis.
 */
public class TaxiDto {

    private Long taxiNumber;

    private String driverFirstName;

    protected String driverLastName;

    public TaxiDto() {
    }

    public TaxiDto(Long taxiNumber, String driverFirstName, String driverLastName) {
        this.taxiNumber = taxiNumber;
        this.driverFirstName = driverFirstName;
        this.driverLastName = driverLastName;
    }

    public Long getTaxiNumber() {
        return taxiNumber;
    }

    public TaxiDto setTaxiNumber(Long taxiNumber) {
        this.taxiNumber = taxiNumber;
        return this;
    }

    public String getDriverFirstName() {
        return driverFirstName;
    }

    public TaxiDto setDriverFirstName(String driverFirstName) {
        this.driverFirstName = driverFirstName;
        return this;
    }

    public String getDriverLastName() {
        return driverLastName;
    }

    public TaxiDto setDriverLastName(String driverLastName) {
        this.driverLastName = driverLastName;
        return this;
    }
}
