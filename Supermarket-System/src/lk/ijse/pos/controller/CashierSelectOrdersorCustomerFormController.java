package lk.ijse.pos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CashierSelectOrdersorCustomerFormController {
    public AnchorPane CashierSelectFormContext;

    public void btnGoBackOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage =(Stage) CashierSelectFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/pos/view/MainForm.fxml"))));
        stage.centerOnScreen();
    }

    public void btnCustomerFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("CashierManageCustomer");
    }

    public void btnOrdersFormOnAction(ActionEvent actionEvent) throws IOException {
        setUi("CashierManageOrders");
    }

    private void setUi(String location) throws IOException {
        Stage stage =(Stage) CashierSelectFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }
}
