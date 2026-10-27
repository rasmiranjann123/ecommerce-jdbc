
package com.kodewala.ecommerce.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.kodewala.ecommerce.config.DBConnection;
import com.kodewala.ecommerce.model.Customer;

public class CustomerRepository {

    public Customer findById(int customerId) throws SQLException {

        String sql = "SELECT customer_id, name, email FROM customers WHERE customer_id = ?";
    

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customerId);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return new Customer(
                            rs.getInt("customer_id"),
                            rs.getString("name"),
                            rs.getString("email")
                    );
                }
            }
        }

        return null;
    }
}

