
package com.kodewala.ecommerce.service;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.kodewala.ecommerce.model.CartItem;
import com.kodewala.ecommerce.repository.CartRepository;

class CartServiceTest {

    private final CartService cartService =
            new CartService(new CartRepository());

    @Test
    void shouldRejectZeroQuantity() {

        assertThrows(IllegalArgumentException.class,
                () -> cartService.addToCart(1, 1, 0));
    }

    @Test
    void shouldRejectNegativeQuantity() {

        assertThrows(IllegalArgumentException.class,
                () -> cartService.addToCart(1, 1, -2));
    }

    @Test
    void shouldRejectInvalidCustomerId() {

        assertThrows(IllegalArgumentException.class,
                () -> cartService.viewCart(0));
    }
    
    
    // Test invalid customer ID while adding to cart
    @Test
    void shouldRejectInvalidCustomerIdWhenAddingToCart() {
        assertThrows(
                IllegalArgumentException.class,
                () -> cartService.addToCart(0, 1, 2)
        );
    }

    // Test invalid product ID while adding to cart
    @Test
    void shouldRejectInvalidProductIdWhenAddingToCart() {
        assertThrows(
                IllegalArgumentException.class,
                () -> cartService.addToCart(1, 0, 2)
        );
    }

    // Test invalid customer ID while removing from cart
    @Test
    void shouldRejectInvalidCustomerIdWhenRemovingFromCart() {
        assertThrows(
                IllegalArgumentException.class,
                () -> cartService.removeFromCart(0, 1)
        );
    }

    // Test invalid product ID while removing from cart
    @Test
    void shouldRejectInvalidProductIdWhenRemovingFromCart() {
        assertThrows(
                IllegalArgumentException.class,
                () -> cartService.removeFromCart(1, 0)
        );
    }

    // Test both IDs invalid while removing from cart
    @Test
    void shouldRejectBothInvalidIdsWhenRemovingFromCart() {
        assertThrows(
                IllegalArgumentException.class,
                () -> cartService.removeFromCart(0, 0)
        );
    }


    @Test
    void shouldCalculateCartItemTotal() {

        CartItem item = new CartItem(
                1,
                "Keyboard",
                new BigDecimal("1500.00"),
                2
        );

        assertEquals(
                new BigDecimal("3000.00"),
                item.getTotalPrice()
        );
    }
}

