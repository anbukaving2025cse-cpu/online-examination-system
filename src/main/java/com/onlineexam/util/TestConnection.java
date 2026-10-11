package com.onlineexam.util;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        try (Connection con = DBConnection.getConnection()) {
            System.out.println("SUCCESS: Connected to MySQL database!");
        } catch (Exception e) {
            System.out.println("FAILED: " + e.getMessage());
        }
    }
}