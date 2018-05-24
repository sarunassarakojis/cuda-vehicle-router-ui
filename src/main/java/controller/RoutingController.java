package controller;

import com.vehicle.router.main.VehicleRouterApp;
import com.vehicle.router.model.Node;
import com.vehicle.router.utils.AlertUtil;
import com.vehicle.router.utils.Triple;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import java.io.IOException;

public class RoutingController {

    @FXML
    private TableColumn nodeDemand;
    @FXML
    private TableColumn nodeY;
    @FXML
    private TableColumn nodeX;
    @FXML
    private TableColumn nodeId;
    @FXML
    private TableView<Node> inputDataTable;

    @FXML
    @SuppressWarnings("unchecked")
    public void initialize() {
        Converter converter = new Converter();

        inputDataTable.setEditable(true);
        nodeId.setCellValueFactory(new PropertyValueFactory<Node, Integer>("id"));
        nodeX.setCellValueFactory(new PropertyValueFactory<Node, Integer>("x"));
        nodeX.setCellFactory(TextFieldTableCell.<String, Integer>forTableColumn(converter));
        nodeX.setOnEditCommit(event -> {
            TableColumn.CellEditEvent<Node, Integer> e = (TableColumn.CellEditEvent<Node, Integer>) event;
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setX(e.getNewValue());
        });
        nodeY.setCellValueFactory(new PropertyValueFactory<Node, Integer>("y"));
        nodeY.setCellFactory(TextFieldTableCell.<String, Integer>forTableColumn(converter));
        nodeY.setOnEditCommit(event -> {
            TableColumn.CellEditEvent<Node, Integer> e = (TableColumn.CellEditEvent<Node, Integer>) event;
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setY(e.getNewValue());
        });
        nodeDemand.setCellValueFactory(new PropertyValueFactory<Node, Integer>("demand"));
        nodeDemand.setCellFactory(TextFieldTableCell.<String, Integer>forTableColumn(converter));
        nodeDemand.setOnEditCommit(event -> {
            TableColumn.CellEditEvent<Node, Integer> e = (TableColumn.CellEditEvent<Node, Integer>) event;
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setDemand(e.getNewValue());
        });
    }

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

    public void deleteEntry(ActionEvent actionEvent) {
        int selectedIndex = inputDataTable.getSelectionModel().getSelectedIndex();

        if (selectedIndex != -1) {
            inputDataTable.getItems().remove(selectedIndex);
        }
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void openOptions(ActionEvent actionEvent) throws IOException {
        inputDataTable.getScene().setRoot(FXMLLoader.load(getClass().getResource("/layout/OptionsWindow.fxml")));
    }

    public void route(ActionEvent actionEvent) {
        route();
    }

    private void route() {
        try {
            Thread.sleep(5000);
            AlertUtil.displayAlert(Alert.AlertType.ERROR, "Unresponsive server", "Server did not respond");
        } catch (InterruptedException e) {
            e.printStackTrace();
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