package com.vehicle.router.controller;

import com.vehicle.router.http.RoutingRequestEntity;
import com.vehicle.router.http.consumer.RoutingServiceConsumer;
import com.vehicle.router.model.Node;
import com.vehicle.router.model.Route;
import com.vehicle.router.utils.AlertUtil;
import com.vehicle.router.utils.Triple;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;

public class RoutingController {

    @FXML
    public TextField depotX;
    @FXML
    public TextField depotY;
    @FXML
    public TextField vehicleCapacity;
    @FXML
    public TabPane tabPane;
    @FXML
    private TableColumn<Node, Integer> nodeDemand;
    @FXML
    private TableColumn<Node, Integer> nodeY;
    @FXML
    private TableColumn<Node, Integer> nodeX;
    @FXML
    private TableColumn<Node, Integer> nodeId;
    @FXML
    private TableView<Node> inputDataTable;
    @FXML
    public TableView<Route> resultsTable;
    @FXML
    public TableColumn<Route, String> route;
    @FXML
    public TableColumn<Route, Integer> metDemand;

    @FXML
    public void initialize() {
        Converter converter = new Converter();

        nodeId.setCellValueFactory(new PropertyValueFactory<>("indice"));

        nodeX.setCellValueFactory(new PropertyValueFactory<>("x"));
        nodeX.setCellFactory(TextFieldTableCell.forTableColumn(converter));
        nodeX.setOnEditCommit(event ->
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setX(event.getNewValue()));

        nodeY.setCellValueFactory(new PropertyValueFactory<>("y"));
        nodeY.setCellFactory(TextFieldTableCell.forTableColumn(converter));
        nodeY.setOnEditCommit(event ->
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setY(event.getNewValue()));

        nodeDemand.setCellValueFactory(new PropertyValueFactory<>("demand"));
        nodeDemand.setCellFactory(TextFieldTableCell.forTableColumn(converter));
        nodeDemand.setOnEditCommit(event ->
                event.getTableView().getItems().get(event.getTablePosition().getRow()).setDemand(event.getNewValue()));

        route.setCellValueFactory(callback -> new SimpleStringProperty(callback.getValue().getNodesAsString()));

        metDemand.setCellValueFactory(new PropertyValueFactory<>("metDemand"));
    }

    @FXML
    public void addNewEntry(ActionEvent actionEvent) {
        Dialog<Triple<Integer, Integer, Integer>> newEntryDialog = new Dialog<>();

        newEntryDialog.setTitle("Add New Entry");
        newEntryDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane entryContent = new GridPane();
        entryContent.setAlignment(Pos.CENTER);
        entryContent.setVgap(10);
        entryContent.setHgap(10);

        Label labelX = new Label("X Coordinate:");
        TextField textX = new TextField();
        textX.setPromptText("X");

        Label labelY = new Label("Y Coordinate:");
        TextField yCoordinate = new TextField();
        yCoordinate.setPromptText("Y");

        Label labelDemand = new Label("Demand:");
        TextField demand = new TextField();
        demand.setPromptText("100");

        entryContent.add(labelX, 0, 0);
        entryContent.add(textX, 1, 0);
        entryContent.add(labelY, 0, 1);
        entryContent.add(yCoordinate, 1, 1);
        entryContent.add(labelDemand, 0, 2);
        entryContent.add(demand, 1, 2);

        newEntryDialog.getDialogPane().setContent(entryContent);
        newEntryDialog.setResultConverter(button -> button == ButtonType.OK ?
                new Triple<>(Integer.valueOf(textX.getText()),
                        Integer.valueOf(yCoordinate.getText()), Integer.valueOf(demand.getText())) : null);

        newEntryDialog.showAndWait().ifPresent(t -> inputDataTable.getItems().add(
                new Node(inputDataTable.getItems().size() + 1, t.first, t.second, t.third)));
    }

    @FXML
    public void deleteEntry(ActionEvent actionEvent) {
        int selectedIndex = inputDataTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex != -1) {
            inputDataTable.getItems().remove(selectedIndex);
        }
    }

    @FXML
    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void openOptions(ActionEvent actionEvent) throws IOException {
        inputDataTable.getScene().setRoot(FXMLLoader.load(getClass().getResource("/layout/OptionsWindow.fxml")));
    }

    @FXML
    public void route(ActionEvent actionEvent) {
        try {
            ObservableList<Route> routes = FXCollections.observableArrayList(
                    RoutingServiceConsumer.consumeParallelRoutingService(constructRoutingRequestData()));

            resultsTable.setItems(routes);
            tabPane.getSelectionModel().select(1);
        } catch (Exception e) {
            AlertUtil.displayExceptionAlert(e, "Failure to Process Request", "Routing request cannot be completed");
        }
    }

    private RoutingRequestEntity constructRoutingRequestData() {
        RoutingRequestEntity requestData = new RoutingRequestEntity(Integer.valueOf(vehicleCapacity.getText()));

        requestData.addNode(new Node(0, Integer.valueOf(depotX.getText()), Integer.valueOf(depotY.getText()), 0));
        inputDataTable.getItems().forEach(requestData::addNode);

        return requestData;
    }

    private static class Converter extends StringConverter<Integer> {
        @Override
        public String toString(Integer object) {
            return object.toString();
        }

        @Override
        public Integer fromString(String string) {
            return Integer.valueOf(string);
        }
    }
}
