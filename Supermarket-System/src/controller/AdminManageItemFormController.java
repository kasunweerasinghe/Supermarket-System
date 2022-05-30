package controller;

import bo.BOFactory;
import bo.SuperBO;
import bo.custom.ItemBO;
import bo.custom.impl.ItemBOImpl;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.ItemDTO;
import view.tm.ItemTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

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

    //Property Injection
    private  ItemBO itemBO =(ItemBO) BOFactory.getBoFactory().getBO(BOFactory.BOType.ITEM);

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
            //Load Off Item
            ArrayList<ItemDTO> getAllItems = itemBO.getAllItem();

            for(ItemDTO item : getAllItems){
                tblItem.getItems().add(new ItemTM(item.getItemCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand()));
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
                itemBO.saveItem(new ItemDTO(code,description,unitPrice,qtyOnHand));

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
                itemBO.updateItem(new ItemDTO(code,description,unitPrice,qtyOnHand));

                ItemTM selectedItem = tblItem.getSelectionModel().getSelectedItem();
                selectedItem.setDescription(description);
                selectedItem.setUnitPrice(unitPrice);
                selectedItem.setQtyOnHand(qtyOnHand);
                tblItem.refresh();

            } catch (SQLException throwables) {
                new Alert(Alert.AlertType.ERROR, throwables.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        btnAddNewItem.fire();

    }

    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        //DI //Tight couping
        return itemBO.existsItem(code);
    }


    public void btnDeleteItemOnAction(ActionEvent actionEvent) {
        //Delete Item
        String code = tblItem.getSelectionModel().getSelectedItem().getItemCode();

        try {

            if(!existItem(code)){
                new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + code).show();
            }
            //DI //Tight couping
            itemBO.deleteItem(code);

            tblItem.getItems().remove(tblItem.getSelectionModel().getSelectedItem());
            tblItem.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException throwables) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the item " + code).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private String generateNewID() {
        Connection connection = null;
        try {
            //DI //Tight couping
            return itemBO.generateItemNewID();

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
