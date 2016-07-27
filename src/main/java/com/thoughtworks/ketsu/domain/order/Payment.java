package com.thoughtworks.ketsu.domain.order;

import java.util.Date;

public class Payment {
    private String payType;
    private double amount;
    private Date time;

    public Payment(String payType, double amount) {
        this.payType = payType;
        this.amount = amount;
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
}
