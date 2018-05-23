package controller;

import com.vehicle.router.utils.AlertUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;

public class RoutingController {

    @FXML
    private TableView inputDataTable;

    @FXML
    public void initialize() {
    }

    public void addNewEntry(ActionEvent actionEvent) {
        AlertUtil.displayAlert(Alert.AlertType.ERROR, "Err", "header");
    }
}
