package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.SQLUtil;
import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.entity.Customer;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {

    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Supermarket.Customer ");
        ArrayList<Customer> allCustomer = new ArrayList<>();
        while(rst.next()){
            allCustomer.add(new Customer(rst.getString(1), rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6)));

        }
        return allCustomer;
    }

    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {

       return SQLUtil.executeUpdate("INSERT INTO Supermarket.Customer (custID, custName, custAddress, custCity, custProvince, custPostalCode) VALUES (?,?,?,?,?,?)"
               ,entity.getCustID(),entity.getCustName(),entity.getCustAddress(),entity.getCustCity(),entity.getCustProvince(),entity.getCustPostalCode());
    }

    @Override
    public boolean update(Customer entity) throws SQLException, ClassNotFoundException {

        return SQLUtil.executeUpdate("UPDATE Supermarket.Customer SET custName=?, custAddress=?, custCity=?, custProvince=?, custPostalCode=? WHERE custID=?"
        ,entity.getCustName(),entity.getCustAddress(),entity.getCustCity(),entity.getCustProvince(),entity.getCustPostalCode(),entity.getCustID());

    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Supermarket.Customer WHERE custID=?", id);
        if(rst.next()){
            return new Customer(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6));
        }
        return null;
    }

    @Override
    public boolean exists(String id) throws SQLException, ClassNotFoundException {

        return SQLUtil.executeQuery("SELECT custID FROM Supermarket.Customer WHERE custID=?", id).next();

    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {

        return SQLUtil.executeUpdate("DELETE FROM Supermarket.Customer WHERE custID=?",id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT custID FROM Supermarket.Customer ORDER BY custID DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("custID");
            int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        } else {
            return "C00-001";
        }

    }

    @Override
    public ArrayList<Customer> getAllCustomersByAddress(String address) throws SQLException, ClassNotFoundException {
        return null;
    }
}
