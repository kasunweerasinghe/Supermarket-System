package dao;

import db.DBConnection;
import model.CustomerDTO;
import model.ItemDTO;

import java.sql.*;
import java.util.ArrayList;

public class ItemDAOImpl implements CrudDAO<ItemDTO,String> {


    @Override
    public ArrayList<ItemDTO> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Supermarket.Item ");
        ArrayList<ItemDTO> getAllItems = new ArrayList<>();
        while (rst.next()){
            getAllItems.add(new ItemDTO(rst.getString(1),rst.getString(2),rst.getBigDecimal(3),rst.getInt(4)));
        }
        return getAllItems;

    }

    @Override
    public boolean save(ItemDTO dto) throws SQLException, ClassNotFoundException {

        return SQLUtil.executeUpdate("INSERT INTO Supermarket.Item(ItemCode,Description,UnitPrice,QtyOnHand) VALUES(?,?,?,?)",dto.getItemCode(),dto.getDescription(),dto.getUnitPrice(),dto.getQtyOnHand());
    }

    @Override
    public boolean update(ItemDTO dto) throws SQLException, ClassNotFoundException {

        return SQLUtil.executeUpdate("UPDATE Supermarket.Item SET Description=?, UnitPrice=?, QtyOnHand=? WHERE ItemCode=?",dto.getDescription(),dto.getUnitPrice(),dto.getQtyOnHand(),dto.getItemCode());
    }

    @Override
    public ItemDTO search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Supermarket.Item WHERE ItemCode=?", id);
        if(rst.next()){
            return new ItemDTO(rst.getString(1),rst.getString(2),rst.getBigDecimal(3),rst.getInt(4));
        }
        return null;
    }

    @Override
    public boolean exists(String code) throws SQLException, ClassNotFoundException {

        return SQLUtil.executeQuery("SELECT ItemCode FROM Supermarket.Item WHERE ItemCode=?", code).next();

    }

    @Override
    public boolean delete(String code) throws SQLException, ClassNotFoundException {

        return SQLUtil.executeUpdate("DELETE FROM Supermarket.Item WHERE ItemCode=?",code);

    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT ItemCode FROM Supermarket.Item ORDER BY ItemCode DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("ItemCode");
            int newItemId = Integer.parseInt(id.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newItemId);
        } else {
            return "I00-001";
        }
    }

}
