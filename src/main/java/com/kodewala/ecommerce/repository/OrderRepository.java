
package com.kodewala.ecommerce.repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.kodewala.ecommerce.config.DBConnection;
import com.kodewala.ecommerce.model.Order;
import com.kodewala.ecommerce.model.OrderItem;

public class OrderRepository {

    public Order saveOrder(Connection con, int customerId,
                           BigDecimal totalAmount) throws SQLException {

        String sql = "INSERT INTO orders (customer_id, total_amount, status) VALUES (?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, customerId);
            ps.setBigDecimal(2, totalAmount);
            ps.setString(3, "PLACED");

            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    long orderId = keys.getLong(1);

                    return new Order(
                            orderId,
                            customerId,
                            LocalDateTime.now(),
                            totalAmount,
                            "PLACED"
                    );
                }
            }
        }

        throw new SQLException("Failed to generate order ID.");
    }

    public void saveOrderItems(Connection con,
                               List<OrderItem> items) throws SQLException {

        String sql = "INSERT INTO order_items (order_id, product_id, quantity, unit_price) VALUES (?, ?, ?, ?)";
 
        try (PreparedStatement ps = con.prepareStatement(sql)) {

            for (OrderItem item : items) {
                ps.setLong(1, item.getOrderId());
                ps.setInt(2, item.getProductId());
                ps.setInt(3, item.getQuantity());
                ps.setBigDecimal(4, item.getUnitPrice());

                ps.addBatch();
            }

            ps.executeBatch();
        }
    }
    
    
    

public List<OrderItem> findOrderItems(long orderId)
        throws SQLException {

    List<OrderItem> items = new ArrayList<>();

    String sql = "SELECT oi.order_item_id, oi.order_id, "
            + "oi.product_id, p.name AS product_name, "
            + "oi.quantity, oi.unit_price "
            + "FROM order_items oi "
            + "JOIN products p ON p.product_id = oi.product_id "
            + "WHERE oi.order_id = ? "
            + "ORDER BY oi.order_item_id";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setLong(1, orderId);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                items.add(new OrderItem(
                        rs.getLong("order_item_id"),
                        rs.getLong("order_id"),
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getInt("quantity"),
                        rs.getBigDecimal("unit_price")
                ));
            }
        }
    }

    return items;
}

    
    

public List<Order> findOrdersByCustomer(int customerId)
        throws SQLException {

    List<Order> orders = new ArrayList<>();

    String sql = "SELECT order_id, customer_id, order_date, "
            + "total_amount, status "
            + "FROM orders "
            + "WHERE customer_id = ? "
            + "ORDER BY order_date DESC";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, customerId);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                orders.add(new Order(
                        rs.getLong("order_id"),
                        rs.getInt("customer_id"),
                        rs.getTimestamp("order_date").toLocalDateTime(),
                        rs.getBigDecimal("total_amount"),
                        rs.getString("status")
                ));
            }
        }
    }

    return orders;
}

}


