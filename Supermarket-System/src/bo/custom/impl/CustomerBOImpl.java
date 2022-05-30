package bo.custom.impl;

import bo.custom.CustomerBO;
import dao.DAOFactory;
import dao.custom.CustomerDAO;
import dto.CustomerDTO;
import entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    //Property Injection
    private CustomerDAO customerDAO =(CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.CUSTOMER);

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll();
        for(Customer customer :all){
            allCustomers.add(new CustomerDTO(customer.getCustID(),customer.getCustName(),customer.getCustAddress(),customer.getCustCity(),customer.getCustProvince(),customer.getCustPostalCode()));
        }
        return allCustomers;
    }

    @Override
    public boolean saveItem(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(dto.getCustID(),dto.getCustName(),dto.getCustAddress(),dto.getCustCity(),dto.getCustProvince(),dto.getCustPostalCode()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getCustID(),dto.getCustName(),dto.getCustAddress(),dto.getCustCity(),dto.getCustProvince(),dto.getCustPostalCode()) );
    }

    @Override
    public boolean customerExists(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exists(id);
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public String generateCustomerNewID() throws SQLException, ClassNotFoundException {
        return customerDAO.generateNewID();
    }

}
