package lk.ijse.pos.view.tm;

public class CustomerTM {
     private String CustID;
     private String custName;
     private String custAddress;
     private String custCity;
     private String custProvince;
     private String custPostalCode;

    public CustomerTM() {
    }

    public CustomerTM(String custID, String custName, String custAddress, String custCity, String custProvince, String custPostalCode) {
        this.CustID = custID;
        this.custName = custName;
        this.custAddress = custAddress;
        this.custCity = custCity;
        this.custProvince = custProvince;
        this.custPostalCode = custPostalCode;
    }

    public String getCustID() {
        return CustID;
    }

    public void setCustID(String custID) {
        CustID = custID;
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

    @Override
    public String toString() {
        return "CustomerTM{" +
                "CustID='" + CustID + '\'' +
                ", custName='" + custName + '\'' +
                ", custAddress='" + custAddress + '\'' +
                ", custCity='" + custCity + '\'' +
                ", custProvince='" + custProvince + '\'' +
                ", custPostalCode='" + custPostalCode + '\'' +
                '}';
    }
}
