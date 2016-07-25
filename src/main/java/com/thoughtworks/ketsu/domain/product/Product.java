package com.thoughtworks.ketsu.domain.product;

import com.mongodb.DBObject;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import java.util.HashMap;
import java.util.Map;

public class Product  {
    private String id;
    private String name;
    private String description;
    private float price;

    public Product(DBObject obj){
        this.id = obj.get("_id").toString();
        this.name = obj.get("name").toString();
        this.description = obj.get("description").toString();
        this.price = Float.valueOf(obj.get("price").toString());

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

    public float getPrice() {
        return price;
    }


}
