package com.kodewala.ecommerce.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	private static final String URL =
            "jdbc:mysql://localhost:3306/ecommerce_db";

    private static final String USER = "root";
    private static final String PASSWORD = "Tutun@22n";

    private DBConnection() {
        // Prevent object creation
    }

    public static Connection getConnection()
            throws SQLException {

        return DriverManager.getConnection(
                URL, USER, PASSWORD);
    }

}
