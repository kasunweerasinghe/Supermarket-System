package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.SQLUtil;
import lk.ijse.pos.dao.custom.QueryDAO;
import lk.ijse.pos.dto.CustomDTO;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {

    @Override
    public ArrayList<CustomDTO>  searchOrderByOrderID(String id)throws SQLException, ClassNotFoundException{
        String sql="select Orders.OrderID,Orders.OrderDate,Orders.custID,OrderDetail.ItemCode,OrderDetail.OrderQTY,OrderDetail.UnitPrice from Orders inner join OrderDetail on Orders.OrderID = OrderDetail.OrderID where Orders.OrderID=?";
        ResultSet rst = SQLUtil.executeQuery(sql, id);
        ArrayList<CustomDTO> orderRecode = new ArrayList();
        while(rst.next()){
            String oid = rst.getString(1);
            String date = rst.getString(2);
            String cusID = rst.getString(3);
            String itemCode = rst.getString(4);
            int qty = rst.getInt(5);
            BigDecimal unitPrice = rst.getBigDecimal(6);

            CustomDTO customDTO = new CustomDTO();
            customDTO.setOrderId(oid);
            customDTO.setOrderDate(LocalDate.now());
            customDTO.setCustomerId(cusID);
            customDTO.setItemCode(itemCode);
            customDTO.setQty(qty);
            customDTO.setUnitPrice(unitPrice);
            orderRecode.add(customDTO);


        }
        return orderRecode;
    }

}
