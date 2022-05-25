package dao;

import db.DBConnection;
import model.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO{

    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Supermarket.Customer ");
        ArrayList<CustomerDTO> allCustomer = new ArrayList<>();
        while(rst.next()){
            allCustomer.add(new CustomerDTO(rst.getString(1), rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6)));

        }
        return allCustomer;
    }

    public boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {

       return SQLUtil.executeUpdate("INSERT INTO Supermarket.Customer (custID, custName, custAddress, custCity, custProvince, custPostalCode) VALUES (?,?,?,?,?,?)"
               ,dto.getCustID(),dto.getCustName(),dto.getCustAddress(),dto.getCustCity(),dto.getCustProvince(),dto.getCustPostalCode());
    }

    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {

        return SQLUtil.executeUpdate("UPDATE Supermarket.Customer SET custName=?, custAddress=?, custCity=?, custProvince=?, custPostalCode=? WHERE custID=?"
        ,dto.getCustName(),dto.getCustAddress(),dto.getCustCity(),dto.getCustProvince(),dto.getCustPostalCode(),dto.getCustID());

    }

    public boolean existsCustomer(String id) throws SQLException, ClassNotFoundException {

        return SQLUtil.executeUpdate("SELECT custID FROM Supermarket.Customer WHERE custID=?", id);
    }

    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {

        return SQLUtil.executeUpdate("DELETE FROM Supermarket.Customer WHERE custID=?",id);
    }

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

}
