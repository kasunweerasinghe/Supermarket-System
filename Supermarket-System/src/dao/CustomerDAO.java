package dao;

import db.DBConnection;
import model.CustomerDTO;

import java.sql.*;
import java.util.ArrayList;

public interface CustomerDAO {

     ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;

     boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;

     boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;

     boolean existsCustomer(String id) throws SQLException, ClassNotFoundException;

     boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;

     String generateNewID() throws SQLException, ClassNotFoundException;
}
