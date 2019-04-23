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
        String sql = "SELECT AgentId,AgtPass FROM agents WHERE AgtUser = ?";
        Connection conn = DBHelper.getConnection();

        try
        {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, agt.getAgtUser());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                dbPassword = rs.getString(2);
                if (Hashing.ValidatePassword(agt.getAgtPassword(),dbPassword)) {
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
}
