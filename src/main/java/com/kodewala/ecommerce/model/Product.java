package com.kodewala.ecommerce.model;

import java.math.BigDecimal;

public class Product {
	
	private int productId;
    private String name;
    private String description;
    private BigDecimal price;
    private int stockQuantity;

    public Product(int productId, String name,
                   String description, BigDecimal price,
                   int stockQuantity) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    @Override
    public String toString() {
        return "Product ID: " + productId
                + ", Name: " + name
                + ", Price: " + price
                + ", Stock: " + stockQuantity;
    }

}
