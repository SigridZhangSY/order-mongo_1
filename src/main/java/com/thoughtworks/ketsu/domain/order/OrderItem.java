package com.thoughtworks.ketsu.domain.order;

public class OrderItem {
    private String productId;
    private int quantity;
    private double amount;

    public OrderItem(String productId, int quantity, double amount) {
        this.productId = productId;
        this.quantity = quantity;
        this.amount = amount;
    }


    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getAmount() {
        return amount;
    }
}
