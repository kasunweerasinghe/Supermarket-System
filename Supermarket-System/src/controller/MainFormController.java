package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class MainFormController {
    public JFXPasswordField pwdPassword;
    public JFXTextField txtUserName;
    public AnchorPane MainLoginFormContext;

    public void btnLogInOnAction(ActionEvent actionEvent) throws IOException {
        String tempUserName = txtUserName.getText();
        String tempPassword = pwdPassword.getText();

        if(tempUserName.equals("admin") && tempPassword.equals("admin123")){
            Stage stage =(Stage) MainLoginFormContext.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminselectReportorItem.fxml"))));
            stage.centerOnScreen();
        }else if(tempUserName.equals("cashier") && tempPassword.equals("cashier1")){
//            Stage stage =(Stage) MainLoginFormContext.getScene().getWindow();
//            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/"))));
        }else {
            new Alert(Alert.AlertType.WARNING, "Try Again!").show();
        }
    }
}
