package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.*;
import db.DBConnection;
import javafx.beans.property.Property;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import model.CustomerDTO;
import model.ItemDTO;
import model.OrderDTO;
import model.OrderDetailDTO;
import view.tm.OrderDetailTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CashierManageOrdersFormController {
    public AnchorPane CashierPlaceOrderFormContext;
    public JFXTextField txtCustomerName;
    public JFXTextField txtItemDescription;
    public JFXComboBox<String> cmbCustomerID;
    public JFXComboBox<String> cmbItemCode;
    public JFXTextField txtQtyOnHand;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQty;
    public TableView<OrderDetailTM> tblOrder;
    public Label lblOrderID;
    public Label lblDate;
    public Label lblTotal;
    public JFXButton btnPlaceOrder;
    public JFXButton btnAdd;
    public TableColumn colItemCode;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colTotal;
    public TableColumn colButton;
    private String orderId;

    //Property Injection
    private final CrudDAO<CustomerDTO,String> customerDAO = new CustomerDAOImpl();
    private final CrudDAO<ItemDTO,String> itemDAO = new ItemDAOImpl();
    private final CrudDAO<OrderDTO,String> orderDAO = new OrderDAOImpl();
    private final CrudDAO<OrderDetailDTO,String> orderDetailDAO = new OrderDetailDAOImpl();

    public void initialize(){
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("Description"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumn<OrderDetailTM, Button> lastCol = (TableColumn<OrderDetailTM, Button>) tblOrder.getColumns().get(5);

        lastCol.setCellValueFactory(param -> {
            Button btnDelete = new Button("Delete");

            btnDelete.setOnAction(event -> {
                tblOrder.getItems().remove(param.getValue());
                tblOrder.getSelectionModel().clearSelection();
                calculateTotal();
                enableOrDisablePlaceOrderButton();
            });

            return new ReadOnlyObjectWrapper<>(btnDelete);
        });

        orderId = generateNewOrderId();
        lblOrderID.setText("Order ID: " + orderId);
        lblDate.setText(LocalDate.now().toString());
        btnPlaceOrder.setDisable(true);
        txtCustomerName.setFocusTraversable(false);
        txtCustomerName.setEditable(false);
        txtItemDescription.setFocusTraversable(false);
        txtItemDescription.setEditable(false);
        txtUnitPrice.setFocusTraversable(false);
        txtUnitPrice.setEditable(false);
        txtQtyOnHand.setFocusTraversable(false);
        txtQtyOnHand.setEditable(false);
        txtQty.setOnAction(event -> btnAdd.fire());
        txtQty.setEditable(false);
        btnAdd.setDisable(true);

        cmbCustomerID.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableOrDisablePlaceOrderButton();

            if (newValue != null) {
                try {
                    /*Search Customer*/
                    Connection connection = DBConnection.getDbConnection().getConnection();
                    try {
                        if (!existCustomer(newValue + "")) {
                            new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + newValue + "").show();
                        }
                        CustomerDTO search = customerDAO.search(newValue + "");

                        txtCustomerName.setText(search.getCustName());
                    } catch (SQLException e) {
                        new Alert(Alert.AlertType.ERROR, "Failed to find the customer " + newValue + "" + e).show();
                    }

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                txtCustomerName.clear();
            }
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newItemCode) -> {
            txtQty.setEditable(newItemCode != null);
            btnAdd.setDisable(newItemCode == null);

            if (newItemCode != null) {

                /*Find Item*/
                try {
                    if (!existItem(newItemCode + "")) {
//                        throw new NotFoundException("There is no such item associated with the id " + code);
                    }

                    //Search Item
                    ItemDTO item = itemDAO.search(newItemCode + "");

                    txtItemDescription.setText(item.getDescription());
                    txtUnitPrice.setText(item.getUnitPrice().setScale(2).toString());

//                    txtQtyOnHand.setText(tblOrderDetails.getItems().stream().filter(detail-> detail.getCode().equals(item.getCode())).<Integer>map(detail-> item.getQtyOnHand() - detail.getQty()).findFirst().orElse(item.getQtyOnHand()) + "");
                    Optional<OrderDetailTM> optOrderDetail = tblOrder.getItems().stream().filter(detail -> detail.getCode().equals(newItemCode)).findFirst();
                    txtQtyOnHand.setText((optOrderDetail.isPresent() ? item.getQtyOnHand() - optOrderDetail.get().getQty() : item.getQtyOnHand()) + "");

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                txtItemDescription.clear();
                txtQty.clear();
                txtQtyOnHand.clear();
                txtUnitPrice.clear();
            }
        });

        tblOrder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, selectedOrderDetail) -> {

            if (selectedOrderDetail != null) {
                cmbItemCode.setDisable(true);
                cmbItemCode.setValue(selectedOrderDetail.getCode());
                btnAdd.setText("Update");
                txtQtyOnHand.setText(Integer.parseInt(txtQtyOnHand.getText()) + selectedOrderDetail.getQty() + "");
                txtQty.setText(selectedOrderDetail.getQty() + "");
            } else {
                btnAdd.setText("Add");
                cmbItemCode.setDisable(false);
                cmbItemCode.getSelectionModel().clearSelection();
                txtQty.clear();
            }

        });


        loadAllCustomerIds();
        loadAllItemCodes();
    }

    private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        //DI
        return itemDAO.exists(code);
    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        //DI
        return customerDAO.exists(id);
    }

    public String generateNewOrderId() {
        try {
            //DI
            return orderDAO.generateNewID();

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "OID-001";
    }

    private void loadAllItemCodes() {
        try {
            //Get All Items
            ArrayList<ItemDTO> all = itemDAO.getAll();
            for(ItemDTO itemDTO : all){
                cmbItemCode.getItems().add(itemDTO.getItemCode());
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadAllCustomerIds() {
        try {
            //Get All Customer
            ArrayList<CustomerDTO> all = customerDAO.getAll();

            for(CustomerDTO customerDTO : all){
                cmbCustomerID.getItems().add(customerDTO.getCustID());
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load customer ids").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void btnAddOrderOnAction(ActionEvent actionEvent) {
        if (!txtQty.getText().matches("\\d+") || Integer.parseInt(txtQty.getText()) <= 0 ||
                Integer.parseInt(txtQty.getText()) > Integer.parseInt(txtQtyOnHand.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
            txtQty.requestFocus();
            txtQty.selectAll();
            return;
        }

        String itemCode = cmbItemCode.getSelectionModel().getSelectedItem();
        String description = txtItemDescription.getText();
        BigDecimal unitPrice = new BigDecimal(txtUnitPrice.getText()).setScale(2);
        int qty = Integer.parseInt(txtQty.getText());
        BigDecimal total = unitPrice.multiply(new BigDecimal(qty)).setScale(2);

        boolean exists = tblOrder.getItems().stream().anyMatch(detail -> detail.getCode().equals(itemCode));
        if (exists) {
            OrderDetailTM orderDetailTM = tblOrder.getItems().stream().filter(detail -> detail.getCode().equals(itemCode)).findFirst().get();

            if (btnAdd.getText().equalsIgnoreCase("Update")) {
                orderDetailTM.setQty(qty);
                orderDetailTM.setTotal(total);
                tblOrder.getSelectionModel().clearSelection();
            } else {
                orderDetailTM.setQty(orderDetailTM.getQty() + qty);
                total = new BigDecimal(orderDetailTM.getQty()).multiply(unitPrice).setScale(2);
                orderDetailTM.setTotal(total);
            }
            tblOrder.refresh();
        } else {
            tblOrder.getItems().add(new OrderDetailTM(itemCode, description, qty, unitPrice, total));
        }
        cmbItemCode.getSelectionModel().clearSelection();
        cmbItemCode.requestFocus();
        calculateTotal();
        enableOrDisablePlaceOrderButton();

    }

    private void enableOrDisablePlaceOrderButton() {
        btnPlaceOrder.setDisable(!(cmbCustomerID.getSelectionModel().getSelectedItem() != null && !tblOrder.getItems().isEmpty()));
    }

    private void calculateTotal() {
        BigDecimal total = new BigDecimal(0);

        for (OrderDetailTM detail : tblOrder.getItems()) {
            total = total.add(detail.getTotal());
        }
        lblTotal.setText("Total: " +total);
    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
        boolean b = saveOrder(orderId, LocalDate.now(), cmbCustomerID.getValue(),
                tblOrder.getItems().stream().map(tm -> new OrderDetailDTO(orderId,tm.getCode(), tm.getQty(), tm.getUnitPrice())).collect(Collectors.toList()));

        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
        }

        orderId = generateNewOrderId();
        lblOrderID.setText("Order Id: " + orderId);
        cmbCustomerID.getSelectionModel().clearSelection();
        cmbItemCode.getSelectionModel().clearSelection();
        tblOrder.getItems().clear();
        txtQty.clear();
        calculateTotal();

    }

    public boolean saveOrder(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails) {
        /*Transaction*/

        try {
            Connection connection = DBConnection.getDbConnection().getConnection();

            /*if order id already exist*/
            if (orderDAO.exists(orderId)) {

            }

            connection.setAutoCommit(false);

            boolean save = orderDAO.save(new OrderDTO(orderId, orderDate, customerId));

            if (!save) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            CrudDAO<ItemDTO,String> itemDAO = new ItemDAOImpl();

            for (OrderDetailDTO detail : orderDetails) {
                boolean save1 = orderDetailDAO.save(detail);

                if (!save1) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }

                //Search & Update Item
                ItemDTO item = findItem(detail.getItemCode());
                item.setQtyOnHand(item.getQtyOnHand() - detail.getQty());

                //Update Item
                boolean update = itemDAO.update(item);

                if (!update) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                    return false;
                }
            }

            connection.commit();
            connection.setAutoCommit(true);
            return true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ItemDTO findItem(String code) {
        try {
            //DI
            return itemDAO.search(code);

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find the Item " + code, e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void btnGoHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage =(Stage) CashierPlaceOrderFormContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CashierSelectOrdersorCustomer.fxml"))));
        stage.centerOnScreen();
    }
}
