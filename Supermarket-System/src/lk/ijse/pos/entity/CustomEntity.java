package lk.ijse.pos.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CustomEntity {
    private String custID;
    private String custName;
    private String custAddress;
    private String custCity;
    private String custProvince;
    private String custPostalCode;

    private String ItemCode;
    private String Description;
    private BigDecimal UnitPrice;
    private int QtyOnHand;

    private String OrderID;
    private int OrderQTY;

    private LocalDate OrderDate;

    public CustomEntity(String string, String rstString, String s, String orderID, int anInt, BigDecimal bigDecimal) {
    }

    public CustomEntity(String custID, String itemCode, BigDecimal unitPrice, String orderID, int orderQTY, LocalDate orderDate) {
        this.custID = custID;
        ItemCode = itemCode;
        UnitPrice = unitPrice;
        OrderID = orderID;
        OrderQTY = orderQTY;
        OrderDate = orderDate;
    }

    public String getCustID() {
        return custID;
    }

    public void setCustID(String custID) {
        this.custID = custID;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }

    public String getCustCity() {
        return custCity;
    }

    public void setCustCity(String custCity) {
        this.custCity = custCity;
    }

    public String getCustProvince() {
        return custProvince;
    }

    public void setCustProvince(String custProvince) {
        this.custProvince = custProvince;
    }

    public String getCustPostalCode() {
        return custPostalCode;
    }

    public void setCustPostalCode(String custPostalCode) {
        this.custPostalCode = custPostalCode;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public BigDecimal getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        UnitPrice = unitPrice;
    }

    public int getQtyOnHand() {
        return QtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        QtyOnHand = qtyOnHand;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public int getOrderQTY() {
        return OrderQTY;
    }

    public void setOrderQTY(int orderQTY) {
        OrderQTY = orderQTY;
    }

    public LocalDate getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        OrderDate = orderDate;
    }
}
