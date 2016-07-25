package com.thoughtworks.ketsu.domain.product;

import com.mongodb.*;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import java.util.HashMap;
import java.util.Map;

public class Product  {
    private String id;
    private String name;
    private String description;
    private double price;

    private MongoClient mongoClient;

    public Product(){

    }
    public Product(BasicDBObject obj){
        this.id = obj.getObjectId("_id").toString();
        this.name = obj.getString("name");
        this.description = obj.getString("description");
        this.price = obj.getDouble("price");
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }


}
