package com.vehicle.router.controller;

import com.vehicle.router.http.RoutingApiTargets;
import com.vehicle.router.main.VehicleRouterApp;
import com.vehicle.router.utils.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.glassfish.jersey.client.ClientResponse;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.ConnectException;

public class OptionsController {

    @FXML
    private TextField serverIp;
    @FXML
    private ComboBox algorithmType;

    @FXML
    public void initialize() {
        algorithmType.getItems().addAll("Sequential", "Parallel");
        algorithmType.getSelectionModel().select(VehicleRouterApp.algorithmType);
        serverIp.setText(VehicleRouterApp.serverIp);
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void cancel(ActionEvent actionEvent) throws IOException {
        serverIp.getScene().setRoot(FXMLLoader.load(getClass().getResource("/layout/RoutingWindow.fxml")));
    }

    public void save(ActionEvent actionEvent) throws IOException {
        VehicleRouterApp.algorithmType = algorithmType.getSelectionModel().getSelectedIndex();
        VehicleRouterApp.serverIp = serverIp.getText();

        serverIp.getScene().setRoot(FXMLLoader.load(getClass().getResource("/layout/RoutingWindow.fxml")));
    }

    public void testConnectionWithServer(ActionEvent actionEvent) {
        try {
            WebTarget target = RoutingApiTargets.getDevicesTarget();
            Response response = target.request(MediaType.APPLICATION_JSON_TYPE)
                    .accept(MediaType.APPLICATION_JSON_TYPE).get();
            AlertUtil.displayAlert(Alert.AlertType.CONFIRMATION, "Success", String.valueOf(response.getStatus()));
        } catch (ProcessingException e) {
            AlertUtil.displayExceptionAlert(e, "Failure to Process Request", "Failed to test connection with a server");
        }
    }
}
