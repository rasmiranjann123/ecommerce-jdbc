
package com.kodewala.ecommerce.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.kodewala.ecommerce.repository.CartRepository;

class OrderValidationTest {

    @Test
    void testInsufficientStock() {

        OrderService orderService =
                new OrderService(new CartRepository());

        assertThrows(
                IllegalArgumentException.class,
                () -> orderService.placeOrder(1)
        );
    }
}

