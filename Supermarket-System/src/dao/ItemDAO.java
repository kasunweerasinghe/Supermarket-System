package dao;

import db.DBConnection;
import model.ItemDTO;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public interface ItemDAO {

     ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException;

     boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException;

     boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException;

     boolean existItem(String code) throws SQLException, ClassNotFoundException;

     boolean deleteItem(String code) throws SQLException, ClassNotFoundException;

     String generateNewID() throws SQLException, ClassNotFoundException;
}
