package edu.hm.network.group12.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Taxi {

    @Id
    @Column(name = "taxi_number")
    private long taxiNumber;

    /**
     * The first name of the driver.
     */
    @Column(name = "driver_first_name")
    private String driverFirstName;

    /**
     * The last name of the driver.
     */
    @Column(name = "driver_last_name")
    private String driverLastName;

    /**
     * The current location of the taxi.
     */
    @Column(name = "origin")
    private String origin;

    /**
     * Destination address of the current trip.
     */
    @Column(name = "destination")
    private String destination;

    /**
     * Estimated travel time of the current trip.
     */
    @Column(name = "duration")
    private int duration;

    /**
     * Indicates if a taxi is available for a new ride or not.
     */
    @Column(name = "available")
    private boolean available;

    public Long getTaxiNumber() {
        return taxiNumber;
    }

    public Taxi setTaxiNumber(Long taxiNumber) {
        this.taxiNumber = taxiNumber;
        return this;
    }

    public String getDriverFirstName() {
        return driverFirstName;
    }

    public Taxi setDriverFirstName(String driverFirstName) {
        this.driverFirstName = driverFirstName;
        return this;
    }

    public String getDriverLastName() {
        return driverLastName;
    }

    public Taxi setDriverLastName(String driverLastName) {
        this.driverLastName = driverLastName;
        return this;
    }

    public String getOrigin() {
        return origin;
    }

    public Taxi setOrigin(String origin) {
        this.origin = origin;
        return this;
    }

    public String getDestination() {
        return destination;
    }

    public Taxi setDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public int getDuration() {
        return duration;
    }

    public Taxi setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public boolean isAvailable() {
        return available;
    }

    public Taxi setAvailable(boolean available) {
        this.available = available;
        return this;
    }
}
