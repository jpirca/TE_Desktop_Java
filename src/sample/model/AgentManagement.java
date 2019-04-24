package sample.model;

import sample.controller.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AgentManagement {
    public static void Register(Agent agt) throws SQLException {
        //hash the password
        var hashedPassword = Hashing.HashPassword(agt.getAgtPassword());

        PreparedStatement stmt = null;
        String sql = "INSERT INTO agents(AgtUser,AgtPass) VALUES(?, ?)";
        Connection conn = DBHelper.getConnection();

        try {
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, agt.getAgtUser());
            stmt.setString(2, hashedPassword);
            stmt.executeUpdate();
            conn.commit();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            if (conn != null) {
                try {
                    System.err.print("Transaction is being rolled back");
                    conn.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
            conn.setAutoCommit(true);
        }
    }

    public static int Authenticate (Agent agt) throws SQLException {
        Integer agtID = 0;
        String dbPassword = null;
        PreparedStatement stmt = null;
        String inputuser = " ";
        String inputpwd = " ";
        if (agt.getAgtUser() != null) {
            inputuser = agt.getAgtUser();
            if (agt.getAgtPassword() != null) {
                inputpwd = agt.getAgtPassword();
            }
        }

        String sql = "SELECT AgentId,AgtPass FROM agents WHERE AgtUser = ?";
        Connection conn = DBHelper.getConnection();

        try
        {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, inputuser);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dbPassword = rs.getString(2);
                if (Hashing.ValidatePassword(inputpwd, dbPassword)) {
                    agtID = rs.getInt(1);
                }
            }
            return agtID;

        }
        catch (Exception ex)
        {
            throw ex;
        }

    }

    public static boolean ChangePassword(int agtID, String oldPassword, String newPassword) throws SQLException {
        Boolean success = false;
        Integer rowsAffected = 0;

        String dbPassword = " ";
        String op = " ";
        if (oldPassword != null) {
            op = oldPassword;
        }
        String np = " ";
        if (newPassword != null) {
            np = newPassword;
        }

        String sql = "SELECT AgtPass FROM agents WHERE AgentId = ?";
        PreparedStatement stmt = null;
        try
        {
            Connection conn = DBHelper.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Integer.toString(agtID));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dbPassword = rs.getString(1);
            }
        }
        catch (Exception ex)
        {
            throw ex;
        }
        if (Hashing.ValidatePassword(op, dbPassword)) {
            np = Hashing.HashPassword(newPassword);

            String updateSql = "UPDATE agents SET AgtPass = ? WHERE AgentId = ?";
            PreparedStatement updateStmt = null;
            try
            {
                Connection conn = DBHelper.getConnection();
                updateStmt = conn.prepareStatement(updateSql);
                updateStmt.setString(1, np);
                updateStmt.setString(2, Integer.toString(agtID));
                rowsAffected = updateStmt.executeUpdate();

            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        if (rowsAffected > 0) {
            success = true;
        }

        return success;
    }



}
