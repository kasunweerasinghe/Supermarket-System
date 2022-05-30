package entity;

import java.math.BigDecimal;

public class OrderDetail {
    private String OrderID;
    private String ItemCode;
    private int OrderQTY;
    private BigDecimal UnitPrice;

    public OrderDetail() {
    }

    public OrderDetail(String orderID, String itemCode, int orderQTY, BigDecimal unitPrice) {
        OrderID = orderID;
        ItemCode = itemCode;
        OrderQTY = orderQTY;
        UnitPrice = unitPrice;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public int getOrderQTY() {
        return OrderQTY;
    }

    public void setOrderQTY(int orderQTY) {
        OrderQTY = orderQTY;
    }

    public BigDecimal getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        UnitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "OrderID='" + OrderID + '\'' +
                ", ItemCode='" + ItemCode + '\'' +
                ", OrderQTY=" + OrderQTY +
                ", UnitPrice=" + UnitPrice +
                '}';
    }
}
