package sample.controller;

import sample.model.Product;
import sample.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierDB {
    public static List<Supplier> getAllSupplier()
    {
        List<Supplier> supList = new ArrayList<>();
        Connection conn = DBHelper.getConnection();
        String sql = "SELECT * FROM suppliers";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                Supplier sup = new Supplier(rs.getInt(1), rs.getString(2));
                supList.add(sup);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supList;
    }

    public static boolean insertSupplier(String supName, int supId)
    {
        boolean result = false;
        Connection conn = DBHelper.getConnection();
        String sql = "INSERT INTO suppliers (SupplierId,SupName) VALUES (?,?)";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,supId);
            stmt.setString(2,supName);
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

    public static boolean checkSupplierUsed(int supId)
    {
        boolean result = false;
        Connection conn = DBHelper.getConnection();
        String sql = "SELECT * FROM products_suppliers WHERE SupplierId=?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,supId);
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

    public static boolean DeleteSupplierUsed(int supId)
    {
        boolean result = false;
        Connection conn = DBHelper.getConnection();
        String sql = "DELETE FROM suppliers WHERE SupplierId=?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,supId);
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
