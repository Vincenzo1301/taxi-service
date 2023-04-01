package edu.hm.network.group12.service;

import edu.hm.network.group12.dto.LightStateDto;

public interface LampService {

    /**
     * Allows the user to turn the lamp with the given lampId on and off
     *
     * @param lampId The lamp which should be switched on or off.
     * @param on Whether the lamp should be switched on or off.
     * @return Whether the request was successful or not.
     */
    boolean turnLightOnOrOff(int lampId, boolean on);

    /**
     * Allows the user to modify the hue and effects.
     *
     * @param lampId The lamp which should be modified.
     * @param lightStateDto The hue and effects which should be used to modify the lamp.
     * @return Whethe the request was successful or not.
     */
    boolean setLightState(int lampId, LightStateDto lightStateDto);
}
