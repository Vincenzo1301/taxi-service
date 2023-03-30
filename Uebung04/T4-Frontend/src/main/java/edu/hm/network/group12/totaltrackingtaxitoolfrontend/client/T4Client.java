package edu.hm.network.group12.totaltrackingtaxitoolfrontend.client;

import edu.hm.network.group12.totaltrackingtaxitoolfrontend.client.dto.TaxiDriveDto;
import edu.hm.network.group12.totaltrackingtaxitoolfrontend.client.dto.TaxiDto;

import java.util.List;
import java.util.Optional;

public interface T4Client {

    /**
     * Post a new taxi drive.
     *
     * @param taxiDriveDto Contains data about the taxi, the origin and destination address.
     */

    /**
     * Post a new taxi drive.
     *
     * @param taxiDriveDto Contains data about the taxi, the origin and destination address.
     * @return Whether an empty optional or an filled optional with created taxi drive.
     */
    Optional<TaxiDriveDto> postTaxiDrive(TaxiDriveDto taxiDriveDto);

    /**
     * Get all existing taxis.
     *
     * @return List of existing taxis.
     */
    List<TaxiDto> getAllTaxis();

    /**
     * Update current taxi drive of given taxi.
     *
     * @param taxiDriveDto Contains data about the taxi, the origin and destination address.
     * @return Whether an empty optional or a filled optional with updated taxi drive.
     */
    Optional<TaxiDriveDto> putTaxiAvailability(TaxiDriveDto taxiDriveDto);

    /**
     * Update available status of given taxi.
     *
     * @param taxiNumber The taxi, which available status should be updated.
     * @param available Whether a taxi is available or not.
     */
    void putTaxiAvailability(long taxiNumber, boolean available);

    /**
     * Delete drive of given taxi.
     *
     * @param taxiNumber The taxi, which drive should be updated.
     */
    void deleteTaxiDrive(long taxiNumber);
}
