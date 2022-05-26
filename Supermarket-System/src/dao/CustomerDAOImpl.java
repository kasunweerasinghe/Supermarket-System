package dao;

import model.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAOImpl implements CrudDAO<CustomerDTO,String> {

    @Override
    public ArrayList<CustomerDTO> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Supermarket.Customer ");
        ArrayList<CustomerDTO> allCustomer = new ArrayList<>();
        while(rst.next()){
            allCustomer.add(new CustomerDTO(rst.getString(1), rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6)));

        }
        return allCustomer;
    }

    @Override
    public boolean save(CustomerDTO dto) throws SQLException, ClassNotFoundException {

       return SQLUtil.executeUpdate("INSERT INTO Supermarket.Customer (custID, custName, custAddress, custCity, custProvince, custPostalCode) VALUES (?,?,?,?,?,?)"
               ,dto.getCustID(),dto.getCustName(),dto.getCustAddress(),dto.getCustCity(),dto.getCustProvince(),dto.getCustPostalCode());
    }

    @Override
    public boolean update(CustomerDTO dto) throws SQLException, ClassNotFoundException {

        return SQLUtil.executeUpdate("UPDATE Supermarket.Customer SET custName=?, custAddress=?, custCity=?, custProvince=?, custPostalCode=? WHERE custID=?"
        ,dto.getCustName(),dto.getCustAddress(),dto.getCustCity(),dto.getCustProvince(),dto.getCustPostalCode(),dto.getCustID());

    }

    @Override
    public CustomerDTO search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Supermarket.Customer WHERE custID=?", id);
        if(rst.next()){
            return new CustomerDTO(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6));
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

}
