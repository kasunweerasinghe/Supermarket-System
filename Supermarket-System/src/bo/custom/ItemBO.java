package bo.custom;

import bo.SuperBO;
import model.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {

     ArrayList<ItemDTO> getAllItem() throws SQLException, ClassNotFoundException;

     boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException;

     boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException;

     boolean existsItem(String id) throws SQLException, ClassNotFoundException;

     boolean deleteItem(String id) throws SQLException, ClassNotFoundException;

     String generateItemNewID() throws SQLException, ClassNotFoundException;
}
