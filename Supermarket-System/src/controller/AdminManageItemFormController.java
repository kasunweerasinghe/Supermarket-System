package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import view.tm.ItemTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;

public class AdminManageItemFormController {
    public AnchorPane AdminManageItemFormContext;
    public JFXTextField txtItemCode;
    public JFXTextField txtItemDescription;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQtyOnHand;
    public TableView<ItemTM> tblItem;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colQtyOnHand;
    public TableColumn colUnitPrice;
    public JFXButton btnSave;
    public JFXButton btnDelete;
    public JFXButton btnAddNewItem;

    public void initialize(){
        colCode.setCellValueFactory(new PropertyValueFactory("ItemCode"));
        colDescription.setCellValueFactory(new PropertyValueFactory("Description"));
        colQtyOnHand.setCellValueFactory(new PropertyValueFactory("QtyOnHand"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory("UnitPrice"));

        initUI();

        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtItemCode.setText(newValue.getItemCode());
                txtItemDescription.setText(newValue.getDescription());
                txtUnitPrice.setText(newValue.getUnitPrice().setScale(2).toString());
                txtQtyOnHand.setText(newValue.getQtyOnHand() + "");

                txtItemCode.setDisable(false);
                txtItemDescription.setDisable(false);
                txtUnitPrice.setDisable(false);
                txtQtyOnHand.setDisable(false);
            }
        });

        txtQtyOnHand.setOnAction(event -> btnSave.fire());
        loadAllItem();
    }

    private void loadAllItem() {
        tblItem.getItems().clear();

        try {
            //Get All Item
            Connection connection = DBConnection.getDbConnection().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Supermarket.Item ");
            while (rst.next()){
                tblItem.getItems().add(new ItemTM(rst.getString("ItemCode"),rst.getString("Description"),rst.getBigDecimal("UnitPrice"),rst.getInt("QtyOnHand")));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnSaveItemOnAction(ActionEvent actionEvent) {
        String code = txtItemCode.getText();
        String description = txtItemDescription.getText();

        if(!description.matches("[A-Za-z0-9 ]+")){
            new Alert(Alert.AlertType.ERROR, " Invalid description").show();
            txtItemDescription.requestFocus();
            return;
        }else if(!txtUnitPrice.getText().matches("^[0-9]+[.]?[0-9]*$")){
             new Alert(Alert.AlertType.ERROR, "Invalid unit price").show();
             txtUnitPrice.requestFocus();
             return;
        }else if(!txtQtyOnHand.getText().matches("^\\d+$")){
            new Alert(Alert.AlertType.ERROR, "Invalid qty on hand").show();
            txtQtyOnHand.requestFocus();
            return;
        }

        int qtyOnHand = Integer.parseInt(txtQtyOnHand.getText());
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);

        if(btnSave.getText().equalsIgnoreCase("save")){
            try {

                if(existItem(code)){
                    new Alert(Alert.AlertType.ERROR, code + " already exists").show();
                }
                //Save Item
                Connection connection = DBConnection.getDbConnection().getConnection();
                PreparedStatement pstm = connection.prepareStatement("INSERT INTO Supermarket.Item(ItemCode,Description,UnitPrice,QtyOnHand) VALUES(?,?,?,?)");
                pstm.setString(1, code);
                pstm.setString(2,description);
                pstm.setBigDecimal(3,unitPrice);
                pstm.setInt(4,qtyOnHand);
                pstm.executeUpdate();
                tblItem.getItems().add(new ItemTM(code,description,unitPrice,qtyOnHand));

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            try {

                if(!existItem(code)){
                    new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + code).show();
                }
                //Update Item
                Connection connection = DBConnection.getDbConnection().getConnection();
                PreparedStatement pstm = connection.prepareStatement("UPDATE Supermarket.Item SET Description=?, UnitPrice=?, QtyOnHand=? WHERE ItemCode=?");
                pstm.setString(1,description);
                pstm.setBigDecimal(2,unitPrice);
                pstm.setInt(3,qtyOnHand);
                pstm.setString(4,code);
                pstm.executeUpdate();

                ItemTM selectedItem = tblItem.getSelectionModel().getSelectedItem();
                selectedItem.setDescription(description);
                selectedItem.setUnitPrice(unitPrice);
                selectedItem.setQtyOnHand(qtyOnHand);
                tblItem.refresh();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        btnAddNewItem.fire();

    }

    public void btnDeleteItemOnAction(ActionEvent actionEvent) {
        //Delete Item
        String code = tblItem.getSelectionModel().getSelectedItem().getItemCode();

        try {

            if(!existItem(code)){
                new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + code).show();
            }
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM Supermarket.Item WHERE ItemCode=?");
            pstm.setString(1, code);
            pstm.executeUpdate();

            tblItem.getItems().remove(tblItem.getSelectionModel().getSelectedItem());
            tblItem.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the item " + code).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT ItemCode FROM Supermarket.Item WHERE ItemCode=?");
        pstm.setString(1, code);
        return pstm.executeQuery().next();
    }

    private String generateNewID() {
        Connection connection = null;
        try {
            connection = DBConnection.getDbConnection().getConnection();
            ResultSet rst = connection.createStatement().executeQuery("SELECT ItemCode FROM Supermarket.Item ORDER BY ItemCode DESC LIMIT 1");
            if (rst.next()) {
                String id = rst.getString("ItemCode");
                int newItemId = Integer.parseInt(id.replace("I00-", "")) + 1;
                return String.format("I00-%03d", newItemId);
            } else {
                return "I00-001";
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "I00-001";
    }

    public void btnGoHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage =(Stage) AdminManageItemFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminselectReportorItem.fxml"))));
        stage.centerOnScreen();
    }

    private void initUI(){
        txtItemCode.clear();
        txtItemDescription.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();

        txtItemCode.setDisable(true);
        txtItemDescription.setDisable(true);
        txtUnitPrice.setDisable(true);
        txtQtyOnHand.setDisable(true);

        txtItemCode.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    public void btnAddNewItemOnAction(ActionEvent actionEvent) {
        txtItemCode.setDisable(false);
        txtItemDescription.setDisable(false);
        txtUnitPrice.setDisable(false);
        txtQtyOnHand.setDisable(false);
        txtItemCode.clear();
        txtItemCode.setText(generateNewID());
        txtItemDescription.clear();
        txtUnitPrice.clear();
        txtQtyOnHand.clear();
        txtItemDescription.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblItem.getSelectionModel().clearSelection();
    }
}
