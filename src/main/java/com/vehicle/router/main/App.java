package com.vehicle.router.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class App extends Application {

    private static BorderPane root = new BorderPane();

    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        Text title = new Text("Welcome!");
        Label userName = new Label("User Name:");
        Label password = new Label("Password:");
        TextField userNameField = new TextField();
        TextField passwordField = new PasswordField();
        Button signIn = new Button("Sign in");
        HBox hBox = new HBox(10);
        final Text actionTarget = new Text();

        title.setId("welcome-text");
        actionTarget.setId("action-target");
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        hBox.getChildren().add(signIn);

        signIn.setOnAction(event -> actionTarget.setText("Sign in pressed"));

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        gridPane.add(title, 0, 0, 2, 1);
        gridPane.add(userName, 0, 1);
        gridPane.add(userNameField, 1, 1);
        gridPane.add(password, 0, 2);
        gridPane.add(passwordField, 1, 2);
        gridPane.add(hBox, 1, 4);
        gridPane.add(actionTarget, 1, 6);

        Scene scene = new Scene(gridPane, 300, 275);

        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("/styles/login.css").toExternalForm());
        primaryStage.setTitle("Vehicle Router UI");
        primaryStage.show();

/*        try
        {
            stage = primaryStage;
            stage.setTitle("Vehicle Router UI");

            stage.setResizable(true);
            stage.setScene(new Scene(root));
            stage.setMinWidth(1000);
            stage.setMinHeight(500);
            stage.getIcons().add(new Image("/images/z-logo01.png"));

            goToMainWindow();

            primaryStage.show();
        }
        catch (Exception ex)
        {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }

    private void goToMainWindow() {
        try {
            root.setCenter(FXMLLoader.load(getClass().getResource("/layout/MainWindow.fxml")));
        } catch (Exception ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
