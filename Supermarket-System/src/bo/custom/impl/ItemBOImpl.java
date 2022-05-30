package bo.custom.impl;

import bo.custom.ItemBO;
import dao.DAOFactory;
import dao.SuperDAO;
import dao.custom.ItemDAO;
import dao.custom.impl.ItemDAOImpl;
import model.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    //Property Injection
    private ItemDAO itemDAO =(ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOType.ITEM);

    @Override
    public ArrayList<ItemDTO> getAllItem() throws SQLException, ClassNotFoundException {

        return itemDAO.getAll();
    }

    @Override
    public boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.save(dto);
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(dto);
    }

    @Override
    public boolean existsItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.exists(id);
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(id);
    }

    @Override
    public String generateItemNewID() throws SQLException, ClassNotFoundException {
        return itemDAO.generateNewID();
    }
}
