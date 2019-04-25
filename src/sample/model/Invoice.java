package sample.model;

import java.util.Date;

public class Invoice {
    private int CustomerId;
    private Date BookingDate;
    private String BookingNo;
    private int TravelerCount;
    private String CustFirstName;
    private String CustLastName;
    private String PkgName;
    private double PkgBasePrice;
    private double BasePrice;
    private String Description;

    public Invoice(int customerId, Date bookingDate, String bookingNo, int travelerCount, String custFirstName, String custLastName, String pkgName, double pkgBasePrice, double basePrice, String description) {
        CustomerId = customerId;
        BookingDate = bookingDate;
        BookingNo = bookingNo;
        TravelerCount = travelerCount;
        CustFirstName = custFirstName;
        CustLastName = custLastName;
        PkgName = pkgName;
        PkgBasePrice = pkgBasePrice;
        BasePrice = basePrice;
        Description = description;
    }

    public Invoice() {

    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public Date getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        BookingDate = bookingDate;
    }

    public String getBookingNo() {
        return BookingNo;
    }

    public void setBookingNo(String bookingNo) {
        BookingNo = bookingNo;
    }

    public int getTravelerCount() {
        return TravelerCount;
    }

    public void setTravelerCount(int travelerCount) {
        TravelerCount = travelerCount;
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

    public String getPkgName() {
        return PkgName;
    }

    public void setPkgName(String pkgName) {
        PkgName = pkgName;
    }

    public double getPkgBasePrice() {
        return PkgBasePrice;
    }

    public void setPkgBasePrice(double pkgBasePrice) {
        PkgBasePrice = pkgBasePrice;
    }

    public double getBasePrice() {
        return BasePrice;
    }

    public void setBasePrice(double basePrice) {
        BasePrice = basePrice;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
