package bo;

import model.CustomerDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBO {
     ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException;

     boolean saveItem(CustomerDTO dto) throws SQLException, ClassNotFoundException;

     boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException;

     boolean customerExists(String id) throws SQLException, ClassNotFoundException;

     boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException;

     String generateCustomerNewID() throws SQLException, ClassNotFoundException;
}
