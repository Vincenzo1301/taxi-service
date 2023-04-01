package edu.hm.network.group12.service;

import edu.hm.network.group12.api.dto.taxi.TaxiDriveDto;
import edu.hm.network.group12.api.dto.taxi.TaxiDto;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * Service to create, read, update and delete taxi data.
 */
public interface TaxiService {

    /**
     * Creates new taxi.
     *
     * @param taxiDto The taxi which has to be created.
     * @return Optional with created taxi.
     */
    TaxiDto createTaxi(@NonNull TaxiDto taxiDto);

    /**
     * Creates a taxi drive with origin and destination address. Triggers a quartz job to check whether a taxi arrived
     * at the destination or not.
     *
     * @param taxiDriveDto Contains trip information for a taxi.
     * @return
     */
    TaxiDriveDto createTaxiDrive(@NonNull TaxiDriveDto taxiDriveDto);

    /**
     * Reads all existing taxis.
     *
     * @return List of all existing taxis.
     */
    List<TaxiDto> readAllTaxis();

    /**
     * Update the available status of a taxi. The change of this status kills automatically every quartz job, if there
     * exists one for the given taxiNumber.
     *
     * @param taxiNumber The taxi, which available status should be updated.
     * @param available Whether a taxi is available or not.
     */
    void updateTaxiAvailability(@NonNull long taxiNumber, boolean available);

    void updateTaxiDrive(@NonNull TaxiDriveDto taxiDriveDto);

    /**
     * Delete the current drive of a taxi. The change of this status kills automatically every quartz job.
     *
     * @param taxiNumber The taxi, which taxi drive should be deleted.
     */
    void deleteTaxiDrive(@NonNull long taxiNumber);
}
