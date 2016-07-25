package com.thoughtworks.ketsu.domain.user;


import com.mongodb.BasicDBObject;

public class User{
    private String id;
    private String name;

    public User(BasicDBObject obj){
        this.id = obj.getObjectId("_id").toString();
        this.name = obj.getString("name");
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }


}
