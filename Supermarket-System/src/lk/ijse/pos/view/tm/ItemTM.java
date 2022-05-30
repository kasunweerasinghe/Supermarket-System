package lk.ijse.pos.view.tm;

import java.math.BigDecimal;

public class ItemTM {
     private String ItemCode;
     private String Description;
     private BigDecimal UnitPrice;
     private int QtyOnHand;

    public ItemTM() {
    }

    public ItemTM(String itemCode, String description, BigDecimal unitPrice, int qtyOnHand) {
        ItemCode = itemCode;
        Description = description;
        UnitPrice = unitPrice;
        QtyOnHand = qtyOnHand;
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

    @Override
    public String toString() {
        return "ItemTM{" +
                "ItemCode='" + ItemCode + '\'' +
                ", Description='" + Description + '\'' +
                ", UnitPrice=" + UnitPrice +
                ", QtyOnHand=" + QtyOnHand +
                '}';
    }
}
