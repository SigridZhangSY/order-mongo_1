package com.thoughtworks.ketsu.domain.user;


import com.mongodb.BasicDBObject;
import com.thoughtworks.ketsu.infrastructure.records.Record;
import com.thoughtworks.ketsu.web.jersey.Routes;

import java.util.HashMap;
import java.util.Map;

public class User implements Record{
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


    @Override
    public Map<String, Object> toRefJson(Routes routes) {
        return toJson(routes);
    }

    @Override
    public Map<String, Object> toJson(Routes routes) {
        return new HashMap<String, Object>(){{
            put("id", String.valueOf(id));
            put("uri", routes.userUri(User.this));
            put("name", name);
        }};
    }
}
