package sample.controller;

import sample.model.Invoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InvoiceDB {
    public static List<Invoice> GetCustomerAccount (int cutomerID) {
        List<Invoice> cust_account = new ArrayList<Invoice>();

        String sql = "SELECT b.CustomerId, b.BookingDate, b.BookingNo, b.TravelerCount, c.CustFirstName, c.CustLastName, " +
                "p.PkgName, p.PkgBasePrice, d.BasePrice, d.Description " +
                "FROM customers AS c INNER JOIN bookings AS b ON b.CustomerId = c.CustomerId " +
                "INNER JOIN packages AS p ON b.PackageId = p.PackageId " +
                "INNER JOIN bookingdetails AS d ON b.BookingId = d.BookingId " +
                "WHERE c.CustomerId = ?";
        PreparedStatement stmt = null;

        try {
            Connection conn = DBHelper.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Integer.toString(cutomerID));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Invoice row = new Invoice();
                row.setCustomerId(rs.getInt(1));
                row.setBookingDate(rs.getDate(2));
                row.setBookingNo(rs.getString(3));
                row.setTravelerCount(rs.getInt(4));
                row.setCustFirstName(rs.getString(5));
                row.setCustLastName(rs.getString(6));
                row.setPkgName(rs.getString(7));
                row.setPkgBasePrice(rs.getDouble(8));
                row.setBasePrice(rs.getDouble(9));
                row.setDescription(rs.getString(10));
                cust_account.add(row);
            }

        } catch (SQLException ex) {
            //throw ex;
        }

        return cust_account;
    }
}
