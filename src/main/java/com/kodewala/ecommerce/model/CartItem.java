package com.kodewala.ecommerce.model;

import java.math.BigDecimal;

public class CartItem {
	
	
	private int productId;
    private String productName;
    private BigDecimal unitPrice;
    private int quantity;

    public CartItem(int productId, String productName,
                    BigDecimal unitPrice, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getTotalPrice() {
        return unitPrice.multiply(
                BigDecimal.valueOf(quantity));
    }

}
