package com.vehicle.router.utils;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;

import java.io.PrintWriter;
import java.io.StringWriter;

public class AlertUtil {

    public static void displayExceptionAlert(Throwable throwable, String title, String header) {
        displayExceptionAlert(throwable, title, header, null);
    }

    public static void displayExceptionAlertLater(Throwable throwable, String title, String header) {
        Platform.runLater(() -> displayExceptionAlert(throwable, title, header, null));
    }

    public static void displayExceptionAlert(Throwable throwable, String title, String header, String content) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        throwable.printStackTrace(printWriter);
        displayAlertWithTextArea(Alert.AlertType.ERROR, title, header, content, stringWriter.toString());
    }

    public static void displayAlertWithTextArea(Alert.AlertType alertType, String title, String header, String content, String areaText) {
        Alert alert = new Alert(alertType, content);
        GridPane gridPane = new GridPane();
        TextArea textArea = new TextArea();

        alert.setTitle(title);
        alert.setHeaderText(header);
        textArea.setText(areaText);
        textArea.setEditable(false);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        gridPane.add(textArea, 0, 0);

        alert.getDialogPane().setExpandableContent(gridPane);
        alert.showAndWait();
    }

    public static void displayAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.show();
    }

    public static void displayAlertLater(Alert.AlertType alertType, String title, String header, String content) {
        Platform.runLater(() -> displayAlert(alertType, title, header, content));
    }

    public static void displayAlert(Alert.AlertType alertType, String title, String header) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.show();
    }

    public static void displayAlertLater(Alert.AlertType alertType, String title, String header) {
        Platform.runLater(() -> displayAlert(alertType, title, header));
    }

    public static void displayAlert(Alert.AlertType alertType, String title) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.show();
    }

    public static boolean confirmationAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.initModality(Modality.APPLICATION_MODAL);

        return alert.showAndWait().get() == ButtonType.OK;
    }
}
