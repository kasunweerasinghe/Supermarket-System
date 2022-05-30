package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.PurchaseOrderBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.db.DBConnection;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.dto.OrderDTO;
import lk.ijse.pos.dto.OrderDetailDTO;
import lk.ijse.pos.entity.Customer;
import lk.ijse.pos.entity.Item;
import lk.ijse.pos.entity.OrderDetail;
import lk.ijse.pos.entity.Orders;
import lk.ijse.pos.dao.custom.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {

    private ItemDAO itemDAO =(ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ITEM);
    private CustomerDAO customerDAO =(CustomerDAO)  DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.CUSTOMER);
    private OrderDAO orderDAO =(OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ORDER);
    private OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ORDERDETAIL);
    private QueryDAO queryDAO =(QueryDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.QUERYDAO);

    @Override
    public boolean purchaseOrder(OrderDTO dto) throws SQLException, ClassNotFoundException {

        /*Transaction*/
        Connection connection = DBConnection.getDbConnection().getConnection();
        /*if order id already exist*/
        if (orderDAO.exists(dto.getOrderId())) {
        }
        connection.setAutoCommit(false);

        boolean save = orderDAO.save(new Orders(dto.getOrderId(), dto.getOrderDate(),dto.getCustomerId()));

        if (!save) {
            connection.rollback();
            connection.setAutoCommit(true);
            return false;
        }

        //CrudDAO<ItemDTO, String> itemDAO = new ItemDAOImpl();

        for (OrderDetailDTO detailDTO : dto.getOrderDetails()) {
            boolean save1 = orderDetailDAO.save(new OrderDetail(detailDTO.getOrderId(),detailDTO.getItemCode(),detailDTO.getQty(),detailDTO.getUnitPrice()));

            if (!save1) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }

            //Search & Update Item
            ItemDTO item = searchItem(detailDTO.getItemCode());
            item.setQtyOnHand(item.getQtyOnHand() - detailDTO.getQty());

            //Update Item
            boolean update = itemDAO.update(new Item(item.getItemCode(),item.getDescription(),item.getUnitPrice(),item.getQtyOnHand()));

            if (!update) {
                connection.rollback();
                connection.setAutoCommit(true);
                return false;
            }
        }

        connection.commit();
        connection.setAutoCommit(true);
        return true;


        //return false;

    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer ent = customerDAO.search(id);
        return new CustomerDTO(ent.getCustID(),ent.getCustName(),ent.getCustAddress(),ent.getCustCity(),ent.getCustProvince(),ent.getCustPostalCode());
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item ent = itemDAO.search(code);
        return new ItemDTO(ent.getItemCode(),ent.getDescription(),ent.getUnitPrice(),ent.getQtyOnHand());
    }

    @Override
    public boolean checkItemIsAvailable(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.exists(code);
    }

    @Override
    public boolean checkCustomerIsAvailable(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.exists(id);
    }

    @Override
    public String generateNewOrderID() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewID();
    }

    @Override
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> all = customerDAO.getAll();
        ArrayList<CustomerDTO> allCustomers = new ArrayList<>();
        for(Customer ent : all){
            allCustomers.add(new CustomerDTO(ent.getCustID(),ent.getCustName(),ent.getCustAddress(),ent.getCustCity(),ent.getCustProvince(),ent.getCustPostalCode()));
        }
        return allCustomers;
    }

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> all = itemDAO.getAll();
        ArrayList<ItemDTO> allItems = new ArrayList<>();
        for (Item ent : all){
            allItems.add(new ItemDTO(ent.getItemCode(),ent.getDescription(),ent.getUnitPrice(),ent.getQtyOnHand()));
        }
        return allItems;
    }

}
