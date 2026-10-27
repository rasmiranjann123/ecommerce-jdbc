
package com.kodewala.ecommerce.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order {

    private long orderId;
    private int customerId;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String status;

    public Order(long orderId, int customerId,
                 LocalDateTime orderDate,
                 BigDecimal totalAmount, String status) {

        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public long getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                '}';
    }
}

