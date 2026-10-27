
package com.kodewala.ecommerce.service;

import java.sql.SQLException;
import java.util.List;

import com.kodewala.ecommerce.model.CartItem;
import com.kodewala.ecommerce.repository.CartRepository;

public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    // Add product to cart
    public void addToCart(int customerId, int productId,
                          int quantity) throws SQLException {

        if (customerId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid customer ID");
        }

        if (productId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid product ID");
        }
        

        if (quantity <= 0) {
            throw new IllegalArgumentException(
                    "Quantity must be greater than zero");
        }

        cartRepository.addItem(customerId, productId, quantity);
    }

    // Remove product from cart
    public void removeFromCart(int customerId, int productId)
            throws SQLException {

        if (customerId <= 0 || productId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid customer or product ID");
        }

        cartRepository.removeItem(customerId, productId);
    }

    // View cart items
    public List<CartItem> viewCart(int customerId)
            throws SQLException {

        if (customerId <= 0) {
            throw new IllegalArgumentException(
                    "Invalid customer ID");
        }

        return cartRepository.findCartItems(customerId);
    }
}
