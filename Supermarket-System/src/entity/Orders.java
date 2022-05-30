package entity;

import java.time.LocalDate;

public class Orders {
    private String OrderID;
    private LocalDate OrderDate;
    private String custID;

    public Orders() {
    }

    public Orders(String orderID, LocalDate orderDate, String custID) {
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

    public LocalDate getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
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
        return "Orders{" +
                "OrderID='" + OrderID + '\'' +
                ", OrderDate=" + OrderDate +
                ", custID='" + custID + '\'' +
                '}';
    }
}
