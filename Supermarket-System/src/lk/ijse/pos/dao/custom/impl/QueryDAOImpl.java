package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.SQLUtil;
import lk.ijse.pos.dao.custom.QueryDAO;
import lk.ijse.pos.dto.CustomDTO;
import lk.ijse.pos.entity.CustomEntity;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public ArrayList<CustomEntity>  searchOrderByOrderID(String id)throws SQLException, ClassNotFoundException{
        String sql="select Orders.OrderID,Orders.OrderDate,Orders.custID,OrderDetail.ItemCode,OrderDetail.OrderQTY,OrderDetail.UnitPrice from Orders inner join OrderDetail on Orders.OrderID = OrderDetail.OrderID where Orders.OrderID=?";
        ResultSet rst = SQLUtil.executeQuery(sql, id);
        ArrayList<CustomEntity> orderRecode = new ArrayList();
        while(rst.next()){
            orderRecode.add(new CustomEntity(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getInt(5),rst.getBigDecimal(6)));

        }
        return orderRecode;
    }

}
