package edu.hm.network.group12.client;

import edu.hm.network.group12.api.dto.hue.LightStateDto;

/**
 * Client to communicate with the REST-Api of the Hue-Bridge to control the lamps.
 */
public interface LampClient {

    /**
     * Allows the user to turn the lamp with the given lampId on and off
     *
     * @param lampId The lamp which should be switched on or off.
     * @param on Whether the lamp should be switched on or off.
     */
    void turnLightOnOrOff(long lampId, boolean on);

    /**
     * Allows the user to modify the hue and effects.
     *
     * @param lampId The lamp which should be modified.
     * @param lightStateDto The hue and effects which should be used to modify the lamp.
     * @return Whethe the request was successful or not.
     */
    boolean setLightState(long lampId, LightStateDto lightStateDto);
}
