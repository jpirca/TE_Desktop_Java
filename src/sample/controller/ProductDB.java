package sample.controller;

import sample.model.Package;
import sample.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDB {
    public static List<Product> getAllProducts()
    {
        List<Product> prodList = new ArrayList<>();
        Connection conn = DBHelper.getConnection();
        String sql = "SELECT * FROM products";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                Product prod = new Product(rs.getInt(1), rs.getString(2));
                prodList.add(prod);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prodList;
    }
}
