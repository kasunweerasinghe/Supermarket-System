package dao;

import db.DBConnection;
import model.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO{

    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Supermarket.Customer ");
        ArrayList<CustomerDTO> allCustomer = new ArrayList<>();
        while(rst.next()){
            String id = rst.getString(1);
            String name = rst.getString(2);
            String address = rst.getString(3);
            String city = rst.getString(4);
            String province = rst.getString(5);
            String postalCode = rst.getString(6);
            allCustomer.add(new CustomerDTO(id,name,address,city,province,postalCode));

        }
        return allCustomer;
    }

    public boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO Supermarket.Customer (custID, custName, custAddress, custCity, custProvince, custPostalCode) VALUES (?,?,?,?,?,?)");
        pstm.setString(1, dto.getCustID());
        pstm.setString(2, dto.getCustName());
        pstm.setString(3, dto.getCustAddress());
        pstm.setString(4,dto.getCustCity());
        pstm.setString(5,dto.getCustProvince());
        pstm.setString(6,dto.getCustPostalCode());
        return pstm.executeUpdate()>0;
    }

    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE Supermarket.Customer SET custName=?, custAddress=?, custCity=?, custProvince=?, custPostalCode=? WHERE custID=?");
        pstm.setString(1, dto.getCustName());
        pstm.setString(2, dto.getCustAddress());
        pstm.setString(3, dto.getCustCity());
        pstm.setString(4, dto.getCustProvince());
        pstm.setString(5, dto.getCustPostalCode());
        pstm.setString(6, dto.getCustID());
        return pstm.executeUpdate()>0;

    }

    public boolean existsCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT custID FROM Supermarket.Customer WHERE custID=?");
        pstm.setString(1, id);
        return pstm.executeQuery().next();
    }

    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM Supermarket.Customer WHERE custID=?");
        pstm.setString(1, id);
        return pstm.executeUpdate()>0;
    }

    public String generateNewID() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        ResultSet rst = connection.createStatement().executeQuery("SELECT custID FROM Supermarket.Customer ORDER BY custID DESC LIMIT 1");

        if (rst.next()) {
            String id = rst.getString("custID");
            int newCustomerId = Integer.parseInt(id.replace("C00-", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        } else {
            return "C00-001";
        }

    }

}
