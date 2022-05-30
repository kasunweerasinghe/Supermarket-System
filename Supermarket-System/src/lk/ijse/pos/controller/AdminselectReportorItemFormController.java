package lk.ijse.pos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminselectReportorItemFormController {

    public AnchorPane AdminSelectReportorItemFormDashboardContext;

    public void btnGoBackOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage =(Stage) AdminSelectReportorItemFormDashboardContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/pos/view/MainForm.fxml"))));
        stage.centerOnScreen();
    }

    public void btnGoReportFormOnAction(ActionEvent actionEvent) throws IOException {
        //setUi("AdminManageReport");
        setUi("AdminManageReport");
    }

    public void btnGoItemFormOnAction(ActionEvent actionEvent) throws IOException {
        //setUi("AdminManageItem");
        setUi("AdminManageItem");
    }

    private void setUi(String location) throws IOException {
        Stage stage =(Stage) AdminSelectReportorItemFormDashboardContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/" + location + ".fxml"))));
        stage.centerOnScreen();

    }

}
