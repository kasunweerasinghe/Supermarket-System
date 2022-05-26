package controller;

import dao.custom.QueryDAO;
import dao.custom.impl.QueryDAOImpl;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
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
            JasperReport compileReport =(JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/reports/Items.jasper"));
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, connection);
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException | SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public void btnGoHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage =(Stage) AdminManageReportFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminselectReportorItem.fxml"))));
        stage.centerOnScreen();
    }
}
