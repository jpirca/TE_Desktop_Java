package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import sample.model.Package;
import sample.model.ProductSupplier;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
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
                Package pck = new Package(rs.getInt(1),
                                            rs.getString(2),
                                            rs.getDate(3),
                                            rs.getDate(4),
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

    public static boolean updatePackage(Package newPck, Package oldPck){
        Connection conn = DBHelper.getConnection();
        //String sql = "Update Packages set PkgName=?,PkgStartDate=?,PkgEndDate=?,PkgDesc=?,PkgBasePrice=?,PkgAgencyCommission=?" +
        //           " Where PackageId=? and PkcName=? and PckDesc=? and PckBasePrice=? and PckAgencyCommission=?";
        String sql = "Update Packages set PkgName=?, PkgDesc=?,PkgBasePrice=?,PkgAgencyCommission=? Where PackageId=? and PkgName=? and PkgDesc=? and PkgBasePrice=? and PkgAgencyCommission=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newPck.getPkgName());
            stmt.setString(2, newPck.getPkgDesc());
            stmt.setDouble(3, newPck.getPkgBasePrice());
            stmt.setDouble(4, newPck.getPkgAgencyCommission());
            stmt.setInt(5,oldPck.getPackageId());
            stmt.setString(6, oldPck.getPkgName());
            stmt.setString(7, oldPck.getPkgDesc());
            stmt.setDouble(8, oldPck.getPkgBasePrice());
            stmt.setDouble(9, newPck.getPkgAgencyCommission());
            int numRows = stmt.executeUpdate();
            if (numRows == 0)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR,"There was an error. Please try later");
                alert.showAndWait();
                return false;
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean insertPackage(Package newPck){
        Connection conn = DBHelper.getConnection();
        String sql = "INSERT INTO packages (PkgName,PkgStartDate,PkgEndDate,PkgDesc,PkgBasePrice,PkgAgencyCommission) values (?,?,?,?,?,?)";
        //String sql = "INSERT INTO packages (PkgName) values (?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, newPck.getPkgName());
            stmt.setDate(2, (Date) newPck.getPkgStartDate());
            stmt.setDate(3, (Date) newPck.getPkgEndDate());
            stmt.setString(4, newPck.getPkgDesc());
            stmt.setDouble(5, newPck.getPkgBasePrice());
            stmt.setDouble(6, newPck.getPkgAgencyCommission());

            int result = stmt.executeUpdate();
            if (result == 0)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR,"There was an error. Please try later");
                alert.showAndWait();
                return false;
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }



    public static List<ProductSupplier> getProductSupplierByPkg(int pkgId)
    {
        List<ProductSupplier> listPS = new ArrayList<>();
        Connection conn = DBHelper.getConnection();
        String sql = "SELECT tb.ProductSupplierId, tb.ProductId, tc.ProdName, tb.SupplierId, td.SupName FROM packages_products_suppliers as ta INNER JOIN products_suppliers as tb ON (ta.ProductSupplierId = tb.ProductSupplierId) INNER JOIN products as tc ON (tb.ProductId = tc.ProductId) INNER JOIN suppliers as td ON (tb.SupplierId = td.SupplierId) WHERE ta.PackageId = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,pkgId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                ProductSupplier ps = new ProductSupplier(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5));

                listPS.add(ps);
            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listPS;
    }

    public static List<ProductSupplier> getAllProductSupplier(int pkgId)
    {
        List<ProductSupplier> listPS = new ArrayList<>();
        Connection conn = DBHelper.getConnection();
        String sql = "SELECT tb.ProductSupplierId, tb.ProductId, tc.ProdName, tb.SupplierId, td.SupName " +
                        "FROM products_suppliers as tb  " +
                        "INNER JOIN products as tc ON (tc.ProductId = tb.ProductId) " +
                        "INNER JOIN suppliers as td ON (td.SupplierId = tb.SupplierId) " +
                        "WHERE tb.ProductSupplierId NOT IN (" +
                                                    "SELECT ta.ProductSupplierId " +
                                                    "FROM packages_products_suppliers as ta " +
                                                    "WHERE ta.PackageId = ?) " +
                        "ORDER BY tb.ProductId";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,pkgId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                ProductSupplier ps = new ProductSupplier(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5));

                listPS.add(ps);
            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listPS;
    }

    public static boolean insertProdSup(ProductSupplier newProdSup, int pkgId){
        Connection conn = DBHelper.getConnection();
        String sql = "INSERT INTO packages_products_suppliers (PackageId,ProductSupplierId) values (?,?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, pkgId);
            stmt.setInt(2, newProdSup.getProductSupplierId());

            int result = stmt.executeUpdate();
            if (result == 0)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR,"There was an error. Please try later");
                alert.showAndWait();
                return false;
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean deleteProdSup(ProductSupplier newProdSup, int pkgId){
        Connection conn = DBHelper.getConnection();
        String sql = "DELETE FROM packages_products_suppliers WHERE PackageId=? AND ProductSupplierId=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, pkgId);
            stmt.setInt(2, newProdSup.getProductSupplierId());

            int result = stmt.executeUpdate();
            if (result == 0)
            {
                Alert alert = new Alert(Alert.AlertType.ERROR,"There was an error. Please try later");
                alert.showAndWait();
                return false;
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static List<ProductSupplier> getAllProductSupplierLinked()
    {
        List<ProductSupplier> listPS = new ArrayList<>();
        Connection conn = DBHelper.getConnection();
        String sql = "SELECT tb.ProductSupplierId, tb.ProductId, tc.ProdName, tb.SupplierId, td.SupName " +
                "FROM products_suppliers as tb  " +
                "INNER JOIN products as tc ON (tc.ProductId = tb.ProductId) " +
                "INNER JOIN suppliers as td ON (td.SupplierId = tb.SupplierId) " +
                "ORDER BY tb.ProductId";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next())
            {
                ProductSupplier ps = new ProductSupplier(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5));

                listPS.add(ps);
            }
            rs.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listPS;
    }

    public static boolean isProductSupplierReady(int productId, int supplierId)
    {
        boolean result = true;
        Connection conn = DBHelper.getConnection();
        String sql = "SELECT ProductSupplierId FROM products_suppliers WHERE ProductId = ? AND SupplierId = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,productId);
            stmt.setInt(2,supplierId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next())
            {
                result = false;
            }
            rs.close();
            conn.close();

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean insertProdSupLink(int productId, int supplierId)
    {
        boolean result = false;
        Connection conn = DBHelper.getConnection();
        String sql = "INSERT INTO products_suppliers (ProductId,SupplierId) VALUES (?,?)";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,productId);
            stmt.setInt(2,supplierId);
            int rows = stmt.executeUpdate();
            if (rows == 0){
                AlertBox.display("Error","There was an error creating the relationship. Please try later","OK");
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

    public static boolean deleteProdSupLink(int prodSupId)
    {
        boolean result = false;
        Connection conn = DBHelper.getConnection();
        String sql = "DELETE FROM products_suppliers WHERE ProductSupplierId=?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,prodSupId);
            int rows = stmt.executeUpdate();
            if (rows == 0){
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

    public static boolean checkProdSupPkg(int prodSupId)
    {
        boolean result = false;
        Connection conn = DBHelper.getConnection();
        String sql = "SELECT * FROM packages_products_suppliers WHERE ProductSupplierId=?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,prodSupId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
            {
                result = true;
            }
            else
            {
                result = false;
            }
            rs.close();
            conn.close();

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean checkPackagesOnProdSup(int pkgId)
    {
        boolean result = true;
        Connection conn = DBHelper.getConnection();
        String sql = "SELECT * FROM packages_products_suppliers WHERE PackageId = ?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,pkgId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
            {
                result = true;
            }
            else
            {
                result = false;
            }
            rs.close();
            conn.close();

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean deletePackage(int pkgId)
    {
        boolean result = false;
        Connection conn = DBHelper.getConnection();
        String sql = "DELETE FROM packages WHERE PackageId=?";
        try{
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,pkgId);
            int rows = stmt.executeUpdate();
            if (rows == 0){
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
}
