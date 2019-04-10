package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.Package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PackagesDB {

    public static ObservableList<Package> getAllPackage(){
        ObservableList<Package> packageList = FXCollections.observableArrayList();
        //List<Package> packageList = null;
        Connection conn = DBHelper.getConnection();
        String sql = "SELECT * FROM packages";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while(rs.next())
            {
                Package pck = new Package(rs.getString(1),
                                            rs.getString(2),
                                            rs.getString(3),
                                            rs.getString(4),
                                            rs.getString(5),
                                            rs.getDouble(6),
                                            rs.getDouble(7));
                packageList.add(pck);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return packageList;
    }

}
