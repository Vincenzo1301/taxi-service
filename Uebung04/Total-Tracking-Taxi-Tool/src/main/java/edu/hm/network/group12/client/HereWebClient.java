package edu.hm.network.group12.client;

import org.springframework.lang.NonNull;

/**
 * Client to communicate with the REST-Api of the Here platform for routing operations.
 */
public interface HereWebClient {

    /**
     * Get the time in seconds it takes to get from the start address to the destination address.
     * <p>
     * For more information visit: https://developer.here.com/documentation/routing-api/dev_guide/topics/use-cases/duration-typical-time-of-day.html
     *
     * @param origin The starting address.
     * @param destination The destination address.
     * @return The duration in seconds it takes to get from the start address to the destination address.
     */
    int getDurationForRoute(@NonNull String origin, @NonNull String destination);
}
