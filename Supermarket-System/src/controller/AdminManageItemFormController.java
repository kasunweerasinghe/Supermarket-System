package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class AdminManageItemFormController {
    public AnchorPane AdminManageItemFormContext;

    public void btnGoHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage =(Stage) AdminManageItemFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminselectReportorItem.fxml"))));
        stage.centerOnScreen();
    }

    public void btnSaveItemOnAction(ActionEvent actionEvent) {

    }

    public void btnDeleteItemOnAction(ActionEvent actionEvent) {

    }
}
