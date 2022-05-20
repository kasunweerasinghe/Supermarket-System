package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class CashierManageOrdersFormController {
    public AnchorPane CashierPlaceOrderFormContext;

    public void btnGoHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage =(Stage) CashierPlaceOrderFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CashierSelectOrdersorCustomer.fxml"))));
        stage.centerOnScreen();
    }

    public void btnAddOrderOnAction(ActionEvent actionEvent) {

    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {

    }
}
