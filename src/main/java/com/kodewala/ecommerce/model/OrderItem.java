
package com.kodewala.ecommerce.model;

import java.math.BigDecimal;

public class OrderItem {

    private long orderItemId;
    private long orderId;
    private int productId;
    private String productName;
    private int quantity;
    private BigDecimal unitPrice;

    public OrderItem(long orderItemId, long orderId,
                     int productId, String productName,
                     int quantity, BigDecimal unitPrice) {

        this.orderItemId = orderItemId;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public long getOrderItemId() {
        return orderItemId;
    }

    public long getOrderId() {
        return orderId;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public BigDecimal getSubtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", subtotal=" + getSubtotal() +
                '}';
    }
}

