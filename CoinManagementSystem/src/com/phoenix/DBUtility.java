package com.phoenix;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtility {

    private static final String URL = "jdbc:mysql://localhost:3306/DatabaseName";
    private static final String USER = "Username";
    private static final String PASSWORD = "Password";

    public static Connection getSQLConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
        return null;
    }
}
