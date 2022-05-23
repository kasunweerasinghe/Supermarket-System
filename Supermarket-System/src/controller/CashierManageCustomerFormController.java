package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class CashierManageCustomerFormController {
    public AnchorPane CashierManageCustomerFormContext;
    public JFXTextField txtCustomerID;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerCity;
    public TableView tblCustomer;
    public JFXTextField txtCustomerProvince;
    public JFXTextField txtCustomerPostalCode;

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
