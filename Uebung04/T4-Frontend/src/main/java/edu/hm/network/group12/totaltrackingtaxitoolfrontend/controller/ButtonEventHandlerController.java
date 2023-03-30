package edu.hm.network.group12.totaltrackingtaxitoolfrontend.controller;

import edu.hm.network.group12.totaltrackingtaxitoolfrontend.client.T4Client;
import edu.hm.network.group12.totaltrackingtaxitoolfrontend.client.dto.TaxiDriveDto;
import edu.hm.network.group12.totaltrackingtaxitoolfrontend.client.dto.TaxiDto;
import edu.hm.network.group12.totaltrackingtaxitoolfrontend.client.impl.T4ClientImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class to handle all button click events.
 */
public class ButtonEventHandlerController {

    @FXML
    private Button createRideButton;

    @FXML
    private Button cancelRideButton;

    @FXML
    private Button updateRideButton;

    @FXML
    private Button updateAvailabilityButton;

    @FXML
    private TextField textFieldOrigin;

    @FXML
    private TextField textFieldDestination;

    @FXML
    private CheckBox checkBox;

    @FXML
    private ChoiceBox<String> choiceBox;

    private T4Client t4Client = new T4ClientImpl();

    @FXML
    private void initialize() {
        final List<TaxiDto> taxiDtoList = t4Client.getAllTaxis();
        final ObservableList<String> list = FXCollections.observableArrayList(taxiDtoList.stream().map(taxiDto -> String.valueOf(taxiDto.getTaxiNumber())).collect(Collectors.toList()));

        choiceBox.setItems(list);
    }

    @FXML
    private void createRide() {
        final String origin = textFieldOrigin.getText();
        final String destination = textFieldDestination.getText();
        final long taxiNumber = Long.parseLong(choiceBox.getValue());

        if (origin.isBlank() || destination.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Please fill out the origin and destination address.");
            alert.showAndWait();
        }

        t4Client.postTaxiDrive(new TaxiDriveDto(taxiNumber, origin, destination, 0));
    }

    @FXML
    private void updateAvailability() {
        final boolean available = checkBox.isSelected();
        final long taxiNumber = Long.parseLong(choiceBox.getValue());
        t4Client.putTaxiAvailability(taxiNumber, available);
    }

    @FXML
    private void cancelRide() {
        final long taxiNumber = Long.parseLong(choiceBox.getValue());
        t4Client.deleteTaxiDrive(taxiNumber);
    }

    @FXML
    private void updateRide() {
        final String origin = textFieldOrigin.getText();
        final String destination = textFieldDestination.getText();
        final long taxiNumber = Long.parseLong(choiceBox.getValue());

        if (origin.isBlank() || destination.isBlank()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText("Please fill out the origin and destination address.");
            alert.showAndWait();
        }

       t4Client.putTaxiAvailability(new TaxiDriveDto(taxiNumber, origin, destination, 0));
    }
}