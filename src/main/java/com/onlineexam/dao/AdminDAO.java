package com.onlineexam.dao;

import com.onlineexam.model.Admin;
import com.onlineexam.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {

    // Returns the Admin if username and password are correct, otherwise null.
    public Admin login(String username, String password) {
        String sql = "SELECT admin_id, username FROM admins WHERE username = ? AND password = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Admin(rs.getInt("admin_id"), rs.getString("username"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Admin login failed: " + e.getMessage());
        }
        return null;
    }
}