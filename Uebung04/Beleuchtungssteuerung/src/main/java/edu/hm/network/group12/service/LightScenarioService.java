package edu.hm.network.group12.service;

public interface LightScenarioService {

    /**
     * This is the scenario One of the lights.
     * It starts with one light and builds up.
     */
    void scenarioOne();

    /**
     *This is the scenario Two of the lights.
     * It reflects the traffic lights.
     */
    void scenarioTwo();

    /**
     *This is the scenario Three of the lights.
     * It starts with all the lights, and they take on a new colour every few seconds.
     */
    void scenarioThree();
}
