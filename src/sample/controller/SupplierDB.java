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
}
