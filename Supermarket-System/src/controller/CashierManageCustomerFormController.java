package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dao.CustomerDAOImpl;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.CustomerDTO;
import view.tm.CustomerTM;
import view.tm.ItemTM;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CashierManageCustomerFormController {
    public AnchorPane CashierManageCustomerFormContext;
    public JFXTextField txtCustomerID;
    public JFXTextField txtCustomerName;
    public JFXTextField txtCustomerAddress;
    public JFXTextField txtCustomerCity;
    public TableView<CustomerTM> tblCustomer;
    public JFXTextField txtCustomerProvince;
    public JFXTextField txtCustomerPostalCode;
    public TableColumn colCustomerID;
    public TableColumn colCustomerName;
    public TableColumn colCustomerAddress;
    public TableColumn colCustomerCity;
    public TableColumn colCustomerProvince;
    public TableColumn colCustomerPostalCode;
    public JFXButton btnAddNewCustomer;
    public JFXButton btnDelete;
    public JFXButton btnSave;

    public void initialize(){
        colCustomerID.setCellValueFactory(new PropertyValueFactory<>("CustID"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("custName"));
        colCustomerAddress.setCellValueFactory(new PropertyValueFactory<>("custAddress"));
        colCustomerCity.setCellValueFactory(new PropertyValueFactory<>("custCity"));
        colCustomerProvince.setCellValueFactory(new PropertyValueFactory<>("custProvince"));
        colCustomerPostalCode.setCellValueFactory(new PropertyValueFactory<>("custPostalCode"));

        initUI();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtCustomerID.setText(newValue.getCustID());
                txtCustomerName.setText(newValue.getCustName());
                txtCustomerAddress.setText(newValue.getCustAddress());
                txtCustomerCity.setText(newValue.getCustCity());
                txtCustomerProvince.setText(newValue.getCustProvince());
                txtCustomerPostalCode.setText(newValue.getCustPostalCode());

                txtCustomerID.setDisable(false);
                txtCustomerName.setDisable(false);
                txtCustomerAddress.setDisable(false);
                txtCustomerCity.setDisable(false);
                txtCustomerProvince.setDisable(false);
                txtCustomerPostalCode.setDisable(false);

            }
        });

        txtCustomerPostalCode.setOnAction(event -> btnSave.fire());
        loadAllCustomers();
    }

    private void loadAllCustomers() {
        tblCustomer.getItems().clear();
        try {
            //get All Customers
            //DI
            CustomerDAOImpl customerDAO = new CustomerDAOImpl();
            ArrayList<CustomerDTO> allCustomers = customerDAO.getAllCustomers();

            for (CustomerDTO customer: allCustomers) {
                tblCustomer.getItems().add(new CustomerTM(customer.getCustID(),customer.getCustName(),customer.getCustAddress(),customer.getCustCity(),customer.getCustProvince(),customer.getCustPostalCode()));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public void btnSaveCustomerOnAction(ActionEvent actionEvent) {
        String id = txtCustomerID.getText();
        String name = txtCustomerName.getText();
        String address = txtCustomerAddress.getText();
        String city = txtCustomerCity.getText();
        String province = txtCustomerProvince.getText();
        String postalCode = txtCustomerPostalCode.getText();

        if (!name.matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").show();
            txtCustomerName.requestFocus();
            return;
        } else if (!address.matches(".{3,}")) {
            new Alert(Alert.AlertType.ERROR, "Address should be at least 3 characters long").show();
            txtCustomerAddress.requestFocus();
            return;
        }else if (!city.matches(".{3,}")) {
            new Alert(Alert.AlertType.ERROR, "city should be at least 3 characters long").show();
            txtCustomerCity.requestFocus();
            return;
        }else if (!province.matches(".{3,}")) {
            new Alert(Alert.AlertType.ERROR, "province should be at least 3 characters long").show();
            txtCustomerProvince.requestFocus();
            return;
        }else if (!postalCode.matches("^[0-9]{5}$")) {
            new Alert(Alert.AlertType.ERROR, "postalCode should be at least 3 characters long").show();
            txtCustomerPostalCode.requestFocus();
            return;
        }

        if(btnSave.getText().equalsIgnoreCase("save")){
            try {

                if(existCustomer(id)){
                    new Alert(Alert.AlertType.ERROR, id + " already exists").show();
                }
                //Save Item
                //DI
                CustomerDAOImpl customerDAO = new CustomerDAOImpl();
                customerDAO.saveCustomer( new CustomerDTO(id,name,address,city,province,postalCode));

                tblCustomer.getItems().add(new CustomerTM(id,name,address,city,province,postalCode));

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else{
            try {

                if(!existCustomer(id)){
                    new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + id).show();
                }
                //Update Customers
                //DI
                CustomerDAOImpl customerDAO = new CustomerDAOImpl();
                customerDAO.updateCustomer(new CustomerDTO(id,address,name,city,province,postalCode));

            } catch (SQLException throwables) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + id + throwables.getMessage()).show();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

                CustomerTM selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();
                selectedCustomer.setCustName(name);
                selectedCustomer.setCustAddress(address);
                selectedCustomer.setCustCity(city);
                selectedCustomer.setCustProvince(province);
                selectedCustomer.setCustPostalCode(postalCode);
                tblCustomer.refresh();


        }
        btnAddNewCustomer.fire();

    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        //DI
        CustomerDAOImpl customerDAO = new CustomerDAOImpl();
        return customerDAO.existsCustomer(id);
    }


    public void btnDeleteCustomerOnAction(ActionEvent actionEvent) {
        /*Delete Customer*/
        String id = tblCustomer.getSelectionModel().getSelectedItem().getCustID();
        try {
            if (!existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }
            //DI
            CustomerDAOImpl customerDAO = new CustomerDAOImpl();
            customerDAO.deleteCustomer(id);

            tblCustomer.getItems().remove(tblCustomer.getSelectionModel().getSelectedItem());
            tblCustomer.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }



    private String generateNewId() {
        try {
            //DI
            CustomerDAOImpl customerDAO = new CustomerDAOImpl();
            return customerDAO.generateNewID();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (tblCustomer.getItems().isEmpty()) {
            return "C00-001";
        } else {
            String id = getLastCustomerId();
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        }


    }

    private String getLastCustomerId() {
        List<CustomerTM> tempCustomersList = new ArrayList<>(tblCustomer.getItems());
        return tempCustomersList.get(tempCustomersList.size() - 1).getCustID();
    }

    public void btnAddNewCustomerOnAction(ActionEvent actionEvent) {
        txtCustomerID.setDisable(false);
        txtCustomerName.setDisable(false);
        txtCustomerAddress.setDisable(false);
        txtCustomerCity.setDisable(false);
        txtCustomerProvince.setDisable(false);
        txtCustomerPostalCode.setDisable(false);

        txtCustomerID.clear();
        txtCustomerID.setText(generateNewId());
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtCustomerCity.clear();
        txtCustomerProvince.clear();
        txtCustomerPostalCode.clear();


        txtCustomerName.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblCustomer.getSelectionModel().clearSelection();
    }

    private void initUI() {
        txtCustomerID.clear();
        txtCustomerName.clear();
        txtCustomerAddress.clear();
        txtCustomerCity.clear();
        txtCustomerProvince.clear();
        txtCustomerPostalCode.clear();

        txtCustomerID.setDisable(true);
        txtCustomerName.setDisable(true);
        txtCustomerAddress.setDisable(true);
        txtCustomerCity.setDisable(true);
        txtCustomerProvince.setDisable(true);
        txtCustomerPostalCode.setDisable(true);

        txtCustomerID.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);

    }

    public void btnGoHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage =(Stage) CashierManageCustomerFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CashierSelectOrdersorCustomer.fxml"))));
        stage.centerOnScreen();
    }
}
