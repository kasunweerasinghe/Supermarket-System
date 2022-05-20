package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class CashierManageCustomerFormController {
    public AnchorPane CashierManageCustomerFormContext;

    public void btnGoHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage =(Stage) CashierManageCustomerFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CashierSelectOrdersorCustomer.fxml"))));
        stage.centerOnScreen();
    }

    public void btnSaveCustomerOnAction(ActionEvent actionEvent) {

    }

    public void btnDeleteCustomerOnAction(ActionEvent actionEvent) {

    }
}
