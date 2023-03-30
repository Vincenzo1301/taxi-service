module edu.hm.network.group12.totaltrackingtaxitoolfrontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.json;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens edu.hm.network.group12.totaltrackingtaxitoolfrontend to javafx.fxml;
    exports edu.hm.network.group12.totaltrackingtaxitoolfrontend;
    exports edu.hm.network.group12.totaltrackingtaxitoolfrontend.controller;
    opens edu.hm.network.group12.totaltrackingtaxitoolfrontend.controller to javafx.fxml;
    opens edu.hm.network.group12.totaltrackingtaxitoolfrontend.client.dto to com.fasterxml.jackson.databind;
}