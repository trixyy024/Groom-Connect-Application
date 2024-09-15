package com.capstone.petgroomingapplication.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectSQL {
    // Replace with your actual details
    private static final String DB_URL = "jdbc:jtds:sqlserver://192.168.254.106:1433/PetGroom";
    private static final String USER = "sa";
    private static final String PASS = "groom";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Register the jTDS JDBC driver
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            if (conn != null) {
                System.out.println("Connection established");
            } else {
                System.out.println("Connection failed");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("JDBC Driver not found");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQL Exception occurred");
        }
        return conn;
    }
}