package dao.custom.impl;

import dao.SQLUtil;
import dao.custom.ItemDAO;
import dto.ItemDTO;
import entity.Item;

import java.sql.*;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {

    @Override
    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Supermarket.Item ");
        ArrayList<Item> getAllItems = new ArrayList<>();
        while (rst.next()){
            getAllItems.add(new Item(rst.getString(1),rst.getString(2),rst.getBigDecimal(3),rst.getInt(4)));
        }
        return getAllItems;

    }

    @Override
    public boolean save(Item entity) throws SQLException, ClassNotFoundException {

        return SQLUtil.executeUpdate("INSERT INTO Supermarket.Item(ItemCode,Description,UnitPrice,QtyOnHand) VALUES(?,?,?,?)",entity.getItemCode(),entity.getDescription(),entity.getUnitPrice(),entity.getQtyOnHand());
    }

    @Override
    public boolean update(Item entity) throws SQLException, ClassNotFoundException {

        return SQLUtil.executeUpdate("UPDATE Supermarket.Item SET Description=?, UnitPrice=?, QtyOnHand=? WHERE ItemCode=?",entity.getDescription(),entity.getUnitPrice(),entity.getQtyOnHand(),entity.getItemCode());
    }

    @Override
    public Item search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Supermarket.Item WHERE ItemCode=?", id);
        if(rst.next()){
            return new Item(rst.getString(1),rst.getString(2),rst.getBigDecimal(3),rst.getInt(4));
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

    @Override
    public ArrayList<Item> getItemFromPrice(double price) throws SQLException, ClassNotFoundException {
        return null;
    }
}
