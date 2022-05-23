package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import view.tm.CustomerTM;

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
        loadAllCustomers();
    }

    private void loadAllCustomers() {

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
//                txtCustomerCity.setDisable(false);
//                txtCustomerProvince.setDisable(false);
//                txtCustomerPostalCode.setDisable(false);
            }
        });

        txtCustomerPostalCode.setOnAction(event -> btnSave.fire());
        tblCustomer.getItems().clear();


        try {
            //Get All Customers
            Connection connection = DBConnection.getDbConnection().getConnection();
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM Supermarket.Customer");
            while (rst.next()){
                tblCustomer.getItems().add(new CustomerTM(rst.getString("CustID"),rst.getString("custName"),rst.getString("custAddress"),rst.getString("custCity"),rst.getString("custProvince"),rst.getString("custPostalCode")));

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


        if (btnSave.getText().equalsIgnoreCase("save")) {
            /*Save Customer*/
            try {
                if (existCustomer(id)) {
                    new Alert(Alert.AlertType.ERROR, id + " already exists").show();
                }
                Connection connection = DBConnection.getDbConnection().getConnection();
                PreparedStatement pstm = connection.prepareStatement("INSERT INTO Supermarket.Customer (custID, custName, custAddress, custCity, custProvince, custPostalCode) VALUES (?,?,?,?,?,?)");
                pstm.setString(1, id);
                pstm.setString(2, name);
                pstm.setString(3, address);
                pstm.setString(4,city);
                pstm.setString(5,province);
                pstm.setString(6,postalCode);
                pstm.executeUpdate();

                tblCustomer.getItems().add(new CustomerTM(id,name,address,city,province,postalCode));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            /*Update customer*/
            try {
                if (!existCustomer(id)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
                }
                Connection connection = DBConnection.getDbConnection().getConnection();
                PreparedStatement pstm = connection.prepareStatement("UPDATE Supermarket.Customer SET custName=?, custAddress=?, custCity=?, custProvince=?, custPostalCode=?, custID=?");
                pstm.setString(1, name);
                pstm.setString(2, address);
                pstm.setString(3, city);
                pstm.setString(4, province);
                pstm.setString(5, postalCode);
                pstm.setString(6, id);
                pstm.executeUpdate();

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + id + e.getMessage()).show();
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

    public void btnDeleteCustomerOnAction(ActionEvent actionEvent) {
        /*Delete Customer*/
        String id = tblCustomer.getSelectionModel().getSelectedItem().getCustID();
        try {
            if (!existCustomer(id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + id).show();
            }
            Connection connection = DBConnection.getDbConnection().getConnection();
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM Supermarket.Customer WHERE custID=?");
            pstm.setString(1, id);
            pstm.executeUpdate();

            tblCustomer.getItems().remove(tblCustomer.getSelectionModel().getSelectedItem());
            tblCustomer.getSelectionModel().clearSelection();
            initUI();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the customer " + id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT custID FROM Supermarket.Customer WHERE custID=?");
        pstm.setString(1, id);
        return pstm.executeQuery().next();
    }

    private String generateNewId() {
        try {
            Connection connection = DBConnection.getDbConnection().getConnection();
            ResultSet rst = connection.createStatement().executeQuery("SELECT custID FROM Supermarket.Customer ORDER BY custID DESC LIMIT 1");
            if (rst.next()) {
                String id = rst.getString("custID");
                int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
                return String.format("C00-%03d", newCustomerId);
            } else {
                return "C00-001";
            }
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
