package com.thoughtworks.ketsu.domain.order;

import com.mongodb.DB;
import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import javax.inject.Inject;
import java.util.*;

public class Order implements Record {

    @Inject
    DB db;

    private String id;
    private String userId;
    private String name;
    private String address;
    private String phone;
    private double totalPrice;
    private Date time;
    private List<OrderItem> items;
    private Payment payment;

    public Order(String id, String userId, String name, String address, String phone, double totalPrice, Date time, List<OrderItem> items, Payment payment) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.totalPrice = totalPrice;
        this.time = time;
        this.items = items;
        this.payment = payment;
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


    @Override
    public Map<String, Object> toJson(Routes routes) {
        return new HashMap<String, Object>(){{
            put("uri", routes.orderUri(Order.this));
            put("name", name);
            put("address", address);
            put("phone", phone);
            put("total_price", totalPrice);
            put("create_at", time);
            List<Map<String, Object>> orderItems = new ArrayList<Map<String, Object>>();
            for(int i = 0; i < items.size(); i++){
                Map<String, Object> map = new HashMap<>();
                map.put("product_id", String.valueOf(items.get(i).getProductId()));
                map.put("uri", routes.productUri(String.valueOf(items.get(i).getProductId())));
                map.put("uri", routes.productUri(String.valueOf(items.get(i).getProductId())));
                map.put("quantity", items.get(i).getQuantity());
                map.put("amount", items.get(i).getAmount());
                orderItems.add(map);
            }
            put("order_items", orderItems);
        }};
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return new HashMap<String, Object>() {{
            put("uri", routes.orderUri(Order.this));
            put("name", name);
            put("address", address);
            put("phone", phone);
            put("total_price", totalPrice);
            put("create_at", time);
        }};
    }

    public Payment getPayment() {
        return payment;
    }
}
