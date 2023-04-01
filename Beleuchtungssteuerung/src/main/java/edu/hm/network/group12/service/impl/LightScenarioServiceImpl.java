package edu.hm.network.group12.service.impl;

import edu.hm.network.group12.dto.LightStateDto;
import edu.hm.network.group12.service.LampService;
import edu.hm.network.group12.service.LightScenarioService;

public class LightScenarioServiceImpl implements LightScenarioService {

    private final LampService lampService;

    public LightScenarioServiceImpl() {
        this.lampService = new LampServiceImpl();
    }

    /**
     * Sets all colours to green and switches off the lights.
     */
    public void start(){
        LightStateDto lightStateDto = new LightStateDto.LightStateDtoBuilder().hue(25500).effect("none").alert("none").build();
        lampService.turnLightOnOrOff(1, true);
        lampService.turnLightOnOrOff(2, true);
        lampService.turnLightOnOrOff(3, true);
        lampService.setLightState(1, lightStateDto);
        lampService.setLightState(2, lightStateDto);
        lampService.setLightState(3, lightStateDto);
        lampService.turnLightOnOrOff(1, false);
        lampService.turnLightOnOrOff(2, false);
        lampService.turnLightOnOrOff(3, false);
    }

    @Override
    public void scenarioOne() {
        start();

        for(int index = 1; index<4;index++){
            lampService.turnLightOnOrOff(index, true);
            wait(500);
        }

        int count = 1;
        while(count<4){
            lampService.turnLightOnOrOff(1, false);
            lampService.turnLightOnOrOff(2, false);
            lampService.turnLightOnOrOff(3, false);
            wait(500);
            lampService.turnLightOnOrOff(1, true);
            lampService.turnLightOnOrOff(2, true);
            lampService.turnLightOnOrOff(3, true);
            wait(500);
            count++;
        }

        for(int index = 3; index>0; index--){
            lampService.turnLightOnOrOff(index, false);
            wait(500);
        }
    }

    @Override
    public void scenarioTwo(){
        start();
        LightStateDto lightStateDtoOne = new LightStateDto.LightStateDtoBuilder().hue(0).build();
        lampService.turnLightOnOrOff(1, true);
        lampService.setLightState(1, lightStateDtoOne);
        wait(1500);
        LightStateDto lightStateDtoTwo = new LightStateDto.LightStateDtoBuilder().hue(13000).build();
        lampService.turnLightOnOrOff(2, true);
        lampService.setLightState(2, lightStateDtoTwo);
        wait(1500);
        lampService.turnLightOnOrOff(1, false);
        lampService.turnLightOnOrOff(2, false);
        LightStateDto lightStateDtoBuilder = new LightStateDto.LightStateDtoBuilder().hue(25500).build();
        lampService.turnLightOnOrOff(3, true);
        lampService.setLightState(3, lightStateDtoBuilder);
        wait(2000);
        lampService.turnLightOnOrOff(3, false);
        lampService.turnLightOnOrOff(2, true);
        wait(1000);
        lampService.turnLightOnOrOff(2, false);
        lampService.turnLightOnOrOff(1, true);
    }

    @Override
    public void scenarioThree(){
        start();
        int count = 1;
        int colour = 0;
        while(count<9){
            lampService.turnLightOnOrOff(1, false);
            lampService.turnLightOnOrOff(2, false);
            lampService.turnLightOnOrOff(3, false);
            wait(500);
            lampService.turnLightOnOrOff(1, true);
            lampService.turnLightOnOrOff(2, true);
            lampService.turnLightOnOrOff(3, true);
            LightStateDto lightStateDtoTwo = new LightStateDto.LightStateDtoBuilder().hue(colour).build();
            lampService.setLightState(1, lightStateDtoTwo);
            lampService.setLightState(2, lightStateDtoTwo);
            lampService.setLightState(3, lightStateDtoTwo);
            wait(750);
            count++;
            colour = colour + 8191;
        }
    }

    /**
     * Causes the currently executing thread to sleep for the specified number of milliseconds.
     *
     * @param ms milliseconds
     */
    public static void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
}
