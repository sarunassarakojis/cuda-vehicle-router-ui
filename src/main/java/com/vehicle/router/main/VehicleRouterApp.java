package com.vehicle.router.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VehicleRouterApp extends Application {

    private static BorderPane root = new BorderPane();

    public static int algorithmType = 0;
    public static String serverIp = "http://87.247.109.240:60";

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/layout/RoutingWindow.fxml"));

        primaryStage.setTitle("Vehicle Router UI");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
