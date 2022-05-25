package dao;

import db.DBConnection;
import model.ItemDTO;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO{

    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        Statement stm = connection.createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Supermarket.Item ");
        ArrayList<ItemDTO> getAllItems = new ArrayList<>();
        while (rst.next()){
            String id = rst.getString(1);
            String description = rst.getString(2);
            BigDecimal unitPrice = rst.getBigDecimal(3);
            int qtyOnHand = rst.getInt(4);
            getAllItems.add(new ItemDTO(id,description,unitPrice,qtyOnHand));
        }
        return getAllItems;

    }

    public boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("INSERT INTO Supermarket.Item(ItemCode,Description,UnitPrice,QtyOnHand) VALUES(?,?,?,?)");
        pstm.setString(1, dto.getItemCode());
        pstm.setString(2,dto.getDescription());
        pstm.setBigDecimal(3,dto.getUnitPrice());
        pstm.setInt(4,dto.getQtyOnHand());
        return pstm.executeUpdate()>0;
    }

    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("UPDATE Supermarket.Item SET Description=?, UnitPrice=?, QtyOnHand=? WHERE ItemCode=?");
        pstm.setString(1,dto.getDescription());
        pstm.setBigDecimal(2,dto.getUnitPrice());
        pstm.setInt(3,dto.getQtyOnHand());
        pstm.setString(4,dto.getItemCode());
        return pstm.executeUpdate()>0;
    }

    public boolean existItem(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("SELECT ItemCode FROM Supermarket.Item WHERE ItemCode=?");
        pstm.setString(1, code);
        return pstm.executeQuery().next();
    }

    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        PreparedStatement pstm = connection.prepareStatement("DELETE FROM Supermarket.Item WHERE ItemCode=?");
        pstm.setString(1, code);
        return pstm.executeUpdate()>0;

    }

    public String generateNewID() throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getDbConnection().getConnection();
        connection = DBConnection.getDbConnection().getConnection();
        ResultSet rst = connection.createStatement().executeQuery("SELECT ItemCode FROM Supermarket.Item ORDER BY ItemCode DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("ItemCode");
            int newItemId = Integer.parseInt(id.replace("I00-", "")) + 1;
            return String.format("I00-%03d", newItemId);
        } else {
            return "I00-001";
        }
    }

}
