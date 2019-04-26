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

    public static boolean insertProduct(String prodName)
    {
        boolean result = false;
        Connection conn = DBHelper.getConnection();
        String sql = "INSERT INTO products (ProdName) VALUES (?)";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,prodName);
            int rows = stmt.executeUpdate();
            if (rows==0)
            {
                AlertBox.display("Error","There was an error. Please try later","OK");
                result = false;
            }
            else
            {
                result = true;
            }
            conn.close();

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean checkProductUsed(int prodId)
    {
        boolean result = false;
        Connection conn = DBHelper.getConnection();
        String sql = "SELECT * FROM products_suppliers WHERE ProductId=?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,prodId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
            {
                result = false;
            }
            else{
                result = true;
            }
            rs.close();
            conn.close();

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return  result;
    }

    public static boolean DeleteProductUsed(int prodId)
    {
        boolean result = false;
        Connection conn = DBHelper.getConnection();
        String sql = "DELETE FROM products WHERE ProductId=?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,prodId);
            int rows = stmt.executeUpdate();
            if (rows==0)
            {
                result = false;
            }
            else{
                result = true;
            }
            conn.close();

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return  result;
    }
}
