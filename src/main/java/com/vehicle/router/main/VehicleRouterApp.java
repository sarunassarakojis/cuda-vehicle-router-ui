package com.vehicle.router.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class VehicleRouterApp extends Application {

    public static int algorithmType = 1;
    public static String serverIp = "http://87.247.109.240:6060";

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/layout/RoutingWindow.fxml"));

        System.setProperty("org.graphstream.ui", "javafx");
        primaryStage.setTitle("Vehicle Router UI");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
