
package com.kodewala.ecommerce.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.kodewala.ecommerce.model.Order;
import com.kodewala.ecommerce.model.OrderItem;
import com.kodewala.ecommerce.repository.CartRepository;

class OrderHistoryTest {

    @Test
    void testOrderHistoryAndItems() throws Exception {

        OrderService orderService =
                new OrderService(new CartRepository());

        // Fetch order history for customer ID 1
        List<Order> orders = orderService.getOrderHistory(1);

        assertNotNull(orders);

        System.out.println("Customer Order History:");

        for (Order order : orders) {
            System.out.println(order);

            // Fetch items belonging to this order
            List<OrderItem> items =
                    orderService.getOrderItems(order.getOrderId());

            assertNotNull(items);

            for (OrderItem item : items) {
                System.out.println(item);
            }
        }
    }

    @Test
    void testInvalidCustomerId() {

        OrderService orderService =
                new OrderService(new CartRepository());

        assertThrows(
                IllegalArgumentException.class,
                () -> orderService.getOrderHistory(0)
        );
    }

    @Test
    void testInvalidOrderId() {

        OrderService orderService =
                new OrderService(new CartRepository());

        assertThrows(
                IllegalArgumentException.class,
                () -> orderService.getOrderItems(0)
        );
    }
    
    
    @Test
    void testNonExistingOrderId() throws Exception {

        OrderService orderService =
                new OrderService(new CartRepository());

        List<OrderItem> items =
                orderService.getOrderItems(999999);

        assertNotNull(items);
        assertTrue(items.isEmpty());
    }
}

