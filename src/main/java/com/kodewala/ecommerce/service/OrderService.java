
package com.kodewala.ecommerce.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kodewala.ecommerce.config.DBConnection;
import com.kodewala.ecommerce.model.CartItem;
import com.kodewala.ecommerce.model.Order;
import com.kodewala.ecommerce.model.OrderItem;
import com.kodewala.ecommerce.repository.CartRepository;
import com.kodewala.ecommerce.repository.OrderRepository;
import com.kodewala.ecommerce.repository.ProductRepository;

public class OrderService {

    private final CartRepository cartRepository;

    private final OrderRepository orderRepository;

    public OrderService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
        this.orderRepository = new OrderRepository();
    }

    public void placeOrder(int customerId) throws SQLException {

        if (customerId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid customer ID");
        }

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            try {
                List<CartItem> items =
                        cartRepository.findCartItems(con, customerId);

                if (items.isEmpty()) {
                    throw new IllegalArgumentException(
                            "Cart is empty");
                }

               
                
                validateStock(con, items);
                
                

                BigDecimal totalAmount = BigDecimal.ZERO;

                for (CartItem item : items) {
                	BigDecimal subtotal = item.getTotalPrice();
                	totalAmount = totalAmount.add(subtotal);
                }

                Order order = orderRepository.saveOrder(
                con, customerId, totalAmount);

                List<OrderItem> orderItems = new ArrayList<>();

                for (CartItem item : items) {
                	orderItems.add(new OrderItem(
                			0,
                			order.getOrderId(),
                			item.getProductId(),
                			item.getProductName(),
                			item.getQuantity(),
                			item.getUnitPrice()
                			));
                }
                

                orderRepository.saveOrderItems(con, orderItems);

                ProductRepository productRepository = new ProductRepository();

                for (CartItem item : items) {
                	boolean updated = productRepository.reduceStock(
                			con,
                			item.getProductId(),
                			item.getQuantity()
                			);

                	if (!updated) {
                		throw new SQLException(
                				"Stock update failed for " + item.getProductName());
                	}
                }

                String deleteCartSql =
                		"DELETE FROM cart WHERE customer_id = ?";

                try (PreparedStatement ps = con.prepareStatement(deleteCartSql)) {
                	ps.setInt(1, customerId);
                	ps.executeUpdate();
                }

                con.commit();

                System.out.println("Order placed successfully!");
                System.out.println("Order ID: " + order.getOrderId());
                System.out.println("Total amount: " + order.getTotalAmount());


            } catch (SQLException | RuntimeException e) {
                con.rollback();
                throw e;
            }
        }
    }
    
    
    public List<Order> getOrderHistory(int customerId)
            throws SQLException {

        if (customerId <= 0) {
            throw new IllegalArgumentException("Invalid customer ID");
        }

        return orderRepository.findOrdersByCustomer(customerId);
    }
    
    
    public List<OrderItem> getOrderItems(long orderId)
            throws SQLException {

        if (orderId <= 0) {
            throw new IllegalArgumentException("Invalid order ID");
        }

        return orderRepository.findOrderItems(orderId);
    }
    
    

private void validateStock(Connection con, List<CartItem> items)
        throws SQLException {

    ProductRepository productRepository = new ProductRepository();

    for (CartItem item : items) {

        int availableStock = productRepository.findStockQuantity(
                con, item.getProductId());

        if (availableStock == -1) {
            throw new IllegalArgumentException(
                    "Product not found: " + item.getProductId());
        }

        if (item.getQuantity() > availableStock) {
            throw new IllegalArgumentException(
                    "Insufficient stock for " + item.getProductName());
        }
    }
}

}

