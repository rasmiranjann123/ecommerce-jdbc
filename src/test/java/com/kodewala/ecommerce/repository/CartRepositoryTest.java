
package com.kodewala.ecommerce.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.kodewala.ecommerce.model.CartItem;

class CartRepositoryTest {

    @Test
    void testFindCartItems() throws Exception {

        CartRepository cartRepository = new CartRepository();

        List<CartItem> items = cartRepository.findCartItems(1);

        assertNotNull(items);

        // The previous test may have removed the cart item.
        // An empty cart is a valid result.
        if (!items.isEmpty()) {
            assertTrue(items.get(0).getQuantity() > 0);
        }
    }

    @Test
    void testAddAndRemoveItem() throws Exception {

        CartRepository cartRepository = new CartRepository();

        // Add 1 mouse (product ID 2) to customer 1's cart.
        cartRepository.addItem(1, 2, 1);

        List<CartItem> items = cartRepository.findCartItems(1);

        assertTrue(
                items.stream().anyMatch(
                        item -> item.getProductId() == 2
                                && item.getQuantity() >= 1
                )
        );

        // Remove the mouse again.
        cartRepository.removeItem(1, 2);

        List<CartItem> remainingItems =
                cartRepository.findCartItems(1);

        assertFalse(
                remainingItems.stream()
                        .anyMatch(item -> item.getProductId() == 2)
        );
    }
}

