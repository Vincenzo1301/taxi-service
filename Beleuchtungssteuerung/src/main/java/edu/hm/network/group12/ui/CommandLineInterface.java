package edu.hm.network.group12.ui;

import edu.hm.network.group12.service.LightScenarioService;
import edu.hm.network.group12.service.impl.LampServiceImpl;
import edu.hm.network.group12.service.impl.LightScenarioServiceImpl;

import java.util.Scanner;

public class CommandLineInterface {

    private final Scanner scanner;
    private final LightScenarioService lightScenarioService;

    public CommandLineInterface() {
        this.lightScenarioService = new LightScenarioServiceImpl();
        this.scanner = new Scanner(System.in);
    }

    public void initCommandLineInterface() {
        boolean exit = false;

        while (!exit) {
            System.out.println("Welches Beleuchtungsszenario möchtest du auswählen?");
            System.out.println("[0] Exit");
            System.out.println("[1] Szenario 1");
            System.out.println("[2] Szenario 2");
            System.out.println("[3] Szenario 3");

            int scenario = scanner.nextInt();

            switch (scenario) {
                case 0:
                    exit = true;
                    break;
                case 1: new LightScenarioServiceImpl().scenarioOne();
                    break;
                case 2: new LightScenarioServiceImpl().scenarioTwo();
                    break;
                case 3: new LightScenarioServiceImpl().scenarioThree();
                    break;
            }
        }
        System.out.println("Goodbye!");
    }
}
