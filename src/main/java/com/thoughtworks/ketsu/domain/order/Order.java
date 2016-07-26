package com.thoughtworks.ketsu.domain.order;

import java.util.Date;
import java.util.List;

public class Order {
    private String id;
    private String userId;
    private String name;
    private String address;
    private String phone;
    private double totalPrice;
    private Date time;
    private List<OrderItem> items;

    public Order(String id, String userId, String name, String address, String phone, double totalPrice, Date time, List<OrderItem> items) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.totalPrice = totalPrice;
        this.time = time;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public Date getTime() {
        return time;
    }

    public List<OrderItem> getItems() {
        return items;
    }


}
