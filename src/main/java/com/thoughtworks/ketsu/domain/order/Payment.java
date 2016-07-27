package com.thoughtworks.ketsu.domain.order;

import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Payment implements Record{
    private String payType;
    private double amount;
    private Order order;
    private Date time;

    public Payment(String payType, double amount, Order order) {
        this.payType = payType;
        this.amount = amount;
        this.order = order;
    }

    public String getPayType() {
        return payType;
    }

    public double getAmount() {
        return amount;
    }

    public Date getTime() {
        return time;
    }

    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return toJson(routes);
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return new HashMap<String, Object>(){{
            put("order_uri", routes.orderUri(order));
            put("uri", routes.paymentUri(order.getUserId(), order.getId()));
            put("pay_type", payType);
            put("amount", amount);
        }};
    }
}
