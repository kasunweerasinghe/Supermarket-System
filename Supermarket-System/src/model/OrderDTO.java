package model;

import java.util.Date;

public class OrderDTO {

    private String OrderID;
    private Date OrderDate;
    private String custID;

    public OrderDTO() {
    }

    public OrderDTO(String orderID, Date orderDate, String custID) {
        OrderID = orderID;
        OrderDate = orderDate;
        this.custID = custID;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public Date getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(Date orderDate) {
        OrderDate = orderDate;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "OrderID='" + OrderID + '\'' +
                ", OrderDate=" + OrderDate +
                ", custID='" + custID + '\'' +
                '}';
    }
}
