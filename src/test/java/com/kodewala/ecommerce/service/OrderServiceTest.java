
package com.kodewala.ecommerce.service;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.jupiter.api.Test;

import com.kodewala.ecommerce.config.DBConnection;
import com.kodewala.ecommerce.repository.CartRepository;

class OrderServiceTest {

    @Test
    void testPlaceOrder() throws Exception {

        OrderService orderService =
                new OrderService(new CartRepository());

        orderService.placeOrder(1);

        try (Connection con = DBConnection.getConnection()) {

            // Check whether the cart is empty
            String cartSql =
                    "SELECT COUNT(*) FROM cart WHERE customer_id = ?";

            try (PreparedStatement ps =
                    con.prepareStatement(cartSql)) {

                ps.setInt(1, 1);

                try (ResultSet rs = ps.executeQuery()) {
                    assertTrue(rs.next());
                    assertEquals(0, rs.getInt(1));
                }
            }

            // Check that an order exists for this customer
            String orderSql =
                    "SELECT COUNT(*) FROM orders WHERE customer_id = ?";

            try (PreparedStatement ps =
                    con.prepareStatement(orderSql)) {

                ps.setInt(1, 1);

                try (ResultSet rs = ps.executeQuery()) {
                    assertTrue(rs.next());
                    assertTrue(rs.getInt(1) > 0);
                }
            }
        }
    }
}

