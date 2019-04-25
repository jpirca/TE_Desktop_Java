package sample.model;

public class Customer {
    private int CustomerID;
    private String CustFirstName;
    private String CustLastName;
    private String CustAddress;
    private String CustCity;
    private String CustProvince;
    private String CustPostalCode;
    private String CustCountry;
    private String CustHomePhone;
    private String CustBusinessPhone;
    private String CustEmail;

    public Customer(int customerID, String custFirstName, String custLastName, String custAddress, String custCity,
                    String custProvince, String custPostalCode, String custCountry, String custHomePhone, String custBusinessPhone, String custEmail) {
        CustomerID = customerID;
        CustFirstName = custFirstName;
        CustLastName = custLastName;
        CustAddress = custAddress;
        CustCity = custCity;
        CustProvince = custProvince;
        CustPostalCode = custPostalCode;
        CustCountry = custCountry;
        CustHomePhone = custHomePhone;
        CustBusinessPhone = custBusinessPhone;
        CustEmail = custEmail;
    }

    public Customer() {

    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public String getCustFirstName() {
        return CustFirstName;
    }

    public void setCustFirstName(String custFirstName) {
        CustFirstName = custFirstName;
    }

    public String getCustLastName() {
        return CustLastName;
    }

    public void setCustLastName(String custLastName) {
        CustLastName = custLastName;
    }

    public String getCustAddress() {
        return CustAddress;
    }

    public void setCustAddress(String custAddress) {
        CustAddress = custAddress;
    }

    public String getCustCity() {
        return CustCity;
    }

    public void setCustCity(String custCity) {
        CustCity = custCity;
    }

    public String getCustProvince() {
        return CustProvince;
    }

    public void setCustProvince(String custProvince) {
        CustProvince = custProvince;
    }

    public String getCustPostalCode() {
        return CustPostalCode;
    }

    public void setCustPostalCode(String custPostalCode) {
        CustPostalCode = custPostalCode;
    }

    public String getCustCountry() {
        return CustCountry;
    }

    public void setCustCountry(String custCountry) {
        CustCountry = custCountry;
    }

    public String getCustHomePhone() {
        return CustHomePhone;
    }

    public void setCustHomePhone(String custHomePhone) {
        CustHomePhone = custHomePhone;
    }

    public String getCustBusinessPhone() {
        return CustBusinessPhone;
    }

    public void setCustBusinessPhone(String custBusinessPhone) {
        CustBusinessPhone = custBusinessPhone;
    }

    public String getCustEmail() {
        return CustEmail;
    }

    public void setCustEmail(String custEmail) {
        CustEmail = custEmail;
    }
}
