package com.vehicle.router.controller;

import com.vehicle.router.http.RoutingRequestEntity;
import com.vehicle.router.http.consumer.RoutingServiceConsumer;
import com.vehicle.router.main.VehicleRouterApp;
import com.vehicle.router.model.Node;
import com.vehicle.router.model.Route;
import com.vehicle.router.plotting.DelegateTask;
import com.vehicle.router.plotting.GraphingTool;
import com.vehicle.router.plotting.InputDataChangeListener;
import com.vehicle.router.plotting.RoutesSolveListener;
import com.vehicle.router.utils.AlertUtil;
import com.vehicle.router.utils.CsvReader;
import com.vehicle.router.utils.Triple;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.fx_viewer.FxDefaultView;
import org.graphstream.ui.fx_viewer.FxViewer;
import org.graphstream.ui.javafx.FxGraphRenderer;
import org.graphstream.ui.view.Viewer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RoutingController {

    @FXML
    private TextField depotX;
    @FXML
    private TextField depotY;
    @FXML
    private TextField vehicleCapacity;
    @FXML
    private TabPane tabPane;
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
    private TableView<Route> resultsTable;
    @FXML
    private TableColumn<Route, String> route;
    @FXML
    private TableColumn<Route, Integer> metDemand;
    @FXML
    private VBox stackPane;
    private GraphingTool graphingTool;
    private Graph graph;
    private int nodeIdCounter;

    @FXML
    public void initialize() {
        nodeIdCounter = 1;
        graph = new MultiGraph("vehicle-router-ui-graph");
        graphingTool = new GraphingTool(graph);
        Converter converter = new Converter();
        FxViewer viewer = new FxViewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        FxDefaultView view = (FxDefaultView) viewer.addView(FxViewer.DEFAULT_VIEW_ID, new FxGraphRenderer(), false);

        graph.setAttribute("ui.stylesheet", "url('" + getClass().getResource("/styles/graph.css") + "')");
        nodeId.setCellValueFactory(new PropertyValueFactory<>("indice"));

        nodeX.setCellValueFactory(new PropertyValueFactory<>("x"));
        nodeX.setCellFactory(TextFieldTableCell.forTableColumn(converter));
        nodeX.setOnEditCommit(this::modifyNodeXCoordinate);

        nodeY.setCellValueFactory(new PropertyValueFactory<>("y"));
        nodeY.setCellFactory(TextFieldTableCell.forTableColumn(converter));
        nodeY.setOnEditCommit(this::modifyNodeYCoordinate);

        nodeDemand.setCellValueFactory(new PropertyValueFactory<>("demand"));
        nodeDemand.setCellFactory(TextFieldTableCell.forTableColumn(converter));
        nodeDemand.setOnEditCommit(this::modifyNodeDemand);

        route.setCellValueFactory(callback -> new SimpleStringProperty(callback.getValue().getNodesAsString()));
        metDemand.setCellValueFactory(new PropertyValueFactory<>("metDemand"));

        depotX.textProperty().addListener((observable, oldValue, newValue) ->
                graphingTool.updateDepotX(parseInt(depotX.getText())));

        depotY.textProperty().addListener((observable, oldValue, newValue) ->
                graphingTool.updateDepotY(parseInt(depotY.getText())));

        graphingTool.addDepot(parseInt(depotX.getText()), parseInt(depotY.getText()));
        stackPane.getChildren().add(view);
        inputDataTable.getItems().addListener(new InputDataChangeListener(graphingTool));
        resultsTable.getItems().addListener(new RoutesSolveListener(graphingTool));
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
                new Triple<>(parseInt(textX.getText()), parseInt(yCoordinate.getText()), parseInt(demand.getText())) : null);

        newEntryDialog.showAndWait().ifPresent(t -> inputDataTable.getItems()
                .add(new Node(nodeIdCounter++, t.first, t.second, t.third)));
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
        Platform.exit();
    }

    @FXML
    public void openOptions(ActionEvent actionEvent) throws IOException {
        inputDataTable.getScene().setRoot(FXMLLoader.load(getClass().getResource("/layout/OptionsWindow.fxml")));
    }

    @FXML
    public void route(ActionEvent actionEvent) {
        new Thread(new DelegateTask<>(() -> {
            performRouting();
            return true;
        })).start();
    }

    private void performRouting() {
        try {
            List<Route> routes;

            switch (VehicleRouterApp.algorithmType) {
                case 0:
                    routes = RoutingServiceConsumer.consumeSequentialRoutingService(constructRoutingRequestData());
                    break;
                case 1:
                    routes = RoutingServiceConsumer.consumeParallelRoutingService(constructRoutingRequestData());
                    break;
                default:
                    routes = new ArrayList<>();
            }

            Platform.runLater(() -> {
                resultsTable.getItems().clear();
                resultsTable.getItems().addAll(routes);
                tabPane.getSelectionModel().select(1);
            });
        } catch (Exception e) {
            AlertUtil.displayExceptionAlertLater(e, "Failure to Process Request", "Routing request cannot be completed");
        }

    }

    @FXML
    public void deleteAll(ActionEvent actionEvent) {
        nodeIdCounter = 1;

        inputDataTable.getItems().clear();
        resultsTable.getItems().clear();
    }

    @FXML
    public void loadCsvFile(ActionEvent actionEvent) {
        try {
            File file;
            FileChooser fileChooser = new FileChooser();

            fileChooser.setTitle("Open CSV File");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

            if ((file = fileChooser.showOpenDialog(inputDataTable.getScene().getWindow())) != null) {
                List<Node> nodes = CsvReader.readCsvExcelFile(file);
                nodeIdCounter = nodes.size() + 1;

                inputDataTable.getItems().addAll(nodes);
            }
        } catch (IOException e) {
            AlertUtil.displayExceptionAlert(e, "Error", "Error while parsing CSV file");
        }
    }

    private RoutingRequestEntity constructRoutingRequestData() {
        RoutingRequestEntity requestData = new RoutingRequestEntity(Integer.valueOf(vehicleCapacity.getText()));

        requestData.addNode(new Node(0, parseInt(depotX.getText()), parseInt(depotY.getText()), 0));
        inputDataTable.getItems().forEach(requestData::addNode);

        return requestData;
    }

    private void modifyNodeXCoordinate(TableColumn.CellEditEvent<Node, Integer> event) {
        int row = event.getTablePosition().getRow();
        ObservableList<Node> items = event.getTableView().getItems();
        Node node = items.get(row);

        node.setX(event.getNewValue());
        items.set(row, node);
    }

    private void modifyNodeYCoordinate(TableColumn.CellEditEvent<Node, Integer> event) {
        int row = event.getTablePosition().getRow();
        ObservableList<Node> items = event.getTableView().getItems();
        Node node = items.get(row);

        node.setY(event.getNewValue());
        items.set(row, node);
    }

    private void modifyNodeDemand(TableColumn.CellEditEvent<Node, Integer> event) {
        int row = event.getTablePosition().getRow();
        ObservableList<Node> items = event.getTableView().getItems();

        items.get(row).setDemand(event.getNewValue());
    }

    private static int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
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
