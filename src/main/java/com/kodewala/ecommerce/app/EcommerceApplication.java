package com.kodewala.ecommerce.app;


import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.kodewala.ecommerce.model.CartItem;
import com.kodewala.ecommerce.model.Order;
import com.kodewala.ecommerce.model.OrderItem;
import com.kodewala.ecommerce.model.Product;
import com.kodewala.ecommerce.repository.CartRepository;
import com.kodewala.ecommerce.repository.ProductRepository;
import com.kodewala.ecommerce.service.CartService;
import com.kodewala.ecommerce.service.OrderService;

public class EcommerceApplication {

    public static void main(String[] args) {

        ProductRepository productRepository =
                new ProductRepository();

        CartRepository cartRepository =
                new CartRepository();

        CartService cartService =
                new CartService(cartRepository);

        OrderService orderService =
                new OrderService(cartRepository);

        try (Scanner scanner = new Scanner(System.in)) {

            System.out.println("===== E-COMMERCE SYSTEM =====");
            System.out.print("Enter customer ID: ");

            int customerId = readInt(scanner);

            boolean running = true;

            while (running) {

                System.out.println("\n===== MENU =====");
                System.out.println("1. View all products");
                System.out.println("2. View product details");
                System.out.println("3. Add item to cart");
                System.out.println("4. Remove item from cart");
                System.out.println("5. View cart");
                System.out.println("6. Place order");
                System.out.println("7. View order history");
                System.out.println("8. View items of an order");
                System.out.println("9. Exit");
                System.out.print("Choose an option: ");

                int choice = readInt(scanner);

                try {
                    switch (choice) {

                        case 1:
                            List<Product> products =
                                    productRepository.findAll();

                            if (products.isEmpty()) {
                                System.out.println("No products available.");
                            } else {
                                for (Product product : products) {
                                    System.out.println(product);
                                }
                            }
                            break;

                        case 2:
                            System.out.print("Enter product ID: ");
                            int productId = readInt(scanner);

                            Product product =
                                    productRepository.findById(productId);

                            if (product == null) {
                                System.out.println("Product not found.");
                            } else {
                                System.out.println(product);
                            }
                            break;

                        case 3:
                            System.out.print("Enter product ID: ");
                            int addProductId = readInt(scanner);

                            System.out.print("Enter quantity: ");
                            int quantity = readInt(scanner);

                            cartService.addToCart(customerId, addProductId, quantity);

                            System.out.println("Item added to cart.");
                            break;

                        case 4:
                            System.out.print("Enter product ID to remove: ");
                            int removeProductId = readInt(scanner);

                            cartService.removeFromCart(
                                    customerId,
                                    removeProductId
                            );

                            System.out.println("Item removed from cart.");
                            break;

                        case 5:
                            List<CartItem> cartItems =
                                    cartService.viewCart(customerId);

                            if (cartItems.isEmpty()) {
                                System.out.println("Your cart is empty.");
                            } else {
                                System.out.println("\n===== YOUR CART =====");

                                for (CartItem item : cartItems) {
                                    System.out.println(
                                            item.getProductName()
                                            + " | Quantity: "
                                            + item.getQuantity()
                                            + " | Unit price: "
                                            + item.getUnitPrice()
                                            + " | Subtotal: "
                                            + item.getTotalPrice()
                                    );
                                }
                            }
                            break;

                        case 6:
                            System.out.print(
                                    "Confirm order? Enter yes or no: "
                            );

                            String confirmation = scanner.next();

                            if (confirmation.equalsIgnoreCase("yes")) {
                                orderService.placeOrder(customerId);
                            } else {
                                System.out.println("Order not placed.");
                            }
                            break;

                        case 7:
                            List<Order> orders =
                                    orderService.getOrderHistory(customerId);

                            if (orders.isEmpty()) {
                                System.out.println("No previous orders.");
                            } else {
                                for (Order order : orders) {
                                    System.out.println(order);
                                }
                            }
                            break;

                        case 8:
                            System.out.print("Enter order ID: ");
                            long orderId = readLong(scanner);

                            List<OrderItem> orderItems =
                                    orderService.getOrderItems(orderId);

                            if (orderItems.isEmpty()) {
                                System.out.println(
                                        "No items found for this order."
                                );
                            } else {
                                for (OrderItem item : orderItems) {
                                    System.out.println(item);
                                }
                            }
                            break;

                        case 9:
                            running = false;
                            System.out.println("Thank you for shopping!");
                            break;

                        default:
                            System.out.println(
                                    "Invalid choice. Select 1 to 9."
                            );
                    }

                } catch (SQLException | IllegalArgumentException e) {
                    System.out.println("Operation failed: " + e.getMessage());
                }
            }
        }
    }

    private static int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Enter a valid integer: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static long readLong(Scanner scanner) {
        while (!scanner.hasNextLong()) {
            System.out.print("Enter a valid order ID: ");
            scanner.next();
        }
        return scanner.nextLong();
    }
}
