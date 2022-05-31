package lk.ijse.pos.controller;

import lk.ijse.pos.db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class AdminManageReportFormController {
    public AnchorPane AdminManageReportFormContext;


    public void btnSellingItemsReportOnAction(ActionEvent actionEvent) {
        //String sql = "select OrderDetail.ItemCode,Item.Description,SUM(OrderQTY) AS Total_Quantity from Item inner join OrderDetail on Item.ItemCode = OrderDetail.ItemCode GROUP BY ItemCode ORDER BY SUM(OrderQTY) DESC;";
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            JasperReport compileReport =(JasperReport) JRLoader.loadObject(this.getClass().getResource("/lk/ijse/pos/view/reports/Items.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, connection);
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void btnGetReportByDaily(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            JasperReport compileReport =(JasperReport) JRLoader.loadObject(this.getClass().getResource("/lk/ijse/pos/view/reports/By Daily.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, connection);
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void btnGetReportByMonthly(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            JasperReport compileReport =(JasperReport) JRLoader.loadObject(this.getClass().getResource("/lk/ijse/pos/view/reports/By Monthly.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, connection);
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void btnGetReportByAnualy(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            JasperReport compileReport =(JasperReport) JRLoader.loadObject(this.getClass().getResource("/lk/ijse/pos/view/reports/By Annual .jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, connection);
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void btnGoHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage =(Stage) AdminManageReportFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/lk/ijse/pos/view/AdminselectReportorItem.fxml"))));
        stage.centerOnScreen();
    }


}
