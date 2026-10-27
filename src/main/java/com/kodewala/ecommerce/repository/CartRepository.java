package com.kodewala.ecommerce.repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kodewala.ecommerce.config.DBConnection;
import com.kodewala.ecommerce.model.CartItem;

public class CartRepository {

    // Add product to cart
    public void addItem(int customerId, int productId,
                        int quantity) throws SQLException {

        String sql = "INSERT INTO cart (customer_id, product_id, quantity) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE quantity = quantity + ?";
                
       try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.setInt(4, quantity);

            ps.executeUpdate();
        }
    }

    // Remove product from cart
    public void removeItem(int customerId, int productId)
            throws SQLException {

        String sql = "DELETE FROM cart WHERE customer_id = ? AND product_id = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customerId);
            ps.setInt(2, productId);

            ps.executeUpdate();
        }
    }

    // View cart items
    public List<CartItem> findCartItems(int customerId)
            throws SQLException {

        List<CartItem> items = new ArrayList<>();

        String sql = "SELECT p.product_id, p.name, p.price, c.quantity FROM cart c JOIN products p ON p.product_id = c.product_id WHERE c.customer_id = ? ORDER BY p.product_id";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, customerId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    CartItem item = new CartItem(
                            rs.getInt("product_id"),
                            rs.getString("name"),
                            rs.getBigDecimal("price"),
                            rs.getInt("quantity")
                    );

                    items.add(item);
                }
            }
        }

        return items;
    }
    
    

public List<CartItem> findCartItems(Connection con, int customerId)
        throws SQLException {

    List<CartItem> items = new ArrayList<>();

    String sql = "SELECT p.product_id, p.name, p.price, c.quantity "
            + "FROM cart c "
            + "JOIN products p ON p.product_id = c.product_id "
            + "WHERE c.customer_id = ? "
            + "ORDER BY p.product_id "
            + "FOR UPDATE";

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, customerId);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                items.add(new CartItem(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getBigDecimal("price"),
                        rs.getInt("quantity")
                ));
            }
        }
    }

    return items;
}

}


