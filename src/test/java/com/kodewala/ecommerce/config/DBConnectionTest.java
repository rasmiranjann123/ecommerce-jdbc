package com.kodewala.ecommerce.config;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

public class DBConnectionTest {
	
	@Test
    void shouldConnectToMySQL() throws SQLException {

        try (Connection con = DBConnection.getConnection()) {

            assertNotNull(con);   // -> he connection object must not be null.
            assertFalse(con.isClosed());

            System.out.println(
                    "MySQL connection successful!");
        }
    }
	
}
