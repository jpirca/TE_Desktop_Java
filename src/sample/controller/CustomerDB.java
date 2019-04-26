package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDB {
    public static ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> customerList = FXCollections.observableArrayList();

        Connection conn = DBHelper.getConnection();
        String sql = "SELECT * FROM customers";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                Customer cust = new Customer();
                cust.setCustomerID(rs.getInt(1));
                cust.setCustFirstName(rs.getString(2));
                cust.setCustLastName(rs.getString(3));
                cust.setCustAddress(rs.getString(4));
                cust.setCustCity(rs.getString(5));
                cust.setCustProvince(rs.getString(6));
                cust.setCustPostalCode(rs.getString(7));
                cust.setCustCountry(rs.getString(8));
                cust.setCustHomePhone(rs.getString(9));
                cust.setCustBusinessPhone(rs.getString(10));
                cust.setCustEmail(rs.getString(11));

                customerList.add(cust);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customerList;
    }
}
