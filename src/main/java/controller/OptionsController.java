package controller;

import com.vehicle.router.main.VehicleRouterApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.IOException;

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
}
