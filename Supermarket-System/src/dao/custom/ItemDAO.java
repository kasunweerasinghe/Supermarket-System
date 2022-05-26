package dao.custom;

import dao.CrudDAO;
import model.CustomerDTO;
import model.ItemDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<ItemDTO,String> {

    ArrayList<ItemDTO> getItemFromPrice(double price)throws SQLException, ClassNotFoundException;
}
