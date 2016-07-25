package com.thoughtworks.ketsu.support;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestHelper {
    private static int auto_increment_key = 1;
    public static Map<String, Object> deployment(String appName, String releaseId) {
        return new HashMap<String, Object>() {{
            put("app", String.format("http://service-web.tw.com/apps/%s", appName));
            put("release", String.format("http://service-web.tw.com/apps/%s/releases/%s", appName, releaseId));
        }};
    }

    public static Map<String, Object> stackMap(String id, String name) {
        Map<String, Object> stackMap = new HashMap<String, Object>() {{
            put("id", id);
            put("name", name);
        }};
        return stackMap;
    }

    public static Map<String, Object> productMap(String name){
        return new HashMap<String, Object>(){{
            put("name", name);
            put("description", "red");
            put("price", 1.2);
        }};
    }

    public static Map<String, Object> userMap(String name){
        return new HashMap<String, Object>(){{
            put("name", name);
        }};
    }

    public static Map<String, Object> orderMap( long productId){
        return new HashMap<String, Object>(){{
            put("name", "kayla");
            put("address", "beijing");
            put("phone", "12300000000");
            List<Map<String, Object>> items = new ArrayList<>();
            items.add(new HashMap<String, Object>(){{
                put("product_id", productId);
                put("quantity", 2);
            }});
            put("order_items", items);
        }};
    }

    public static Map<String, Object> paymentMap(){
        return new HashMap<String, Object>(){{
            put("pay_type", "CASH");
            put("amount", 100);
        }};
    }

    public static void clean(String tableName) throws UnknownHostException {
        String dbname = System.getenv().getOrDefault("MONGODB_DATABASE", "mongodb_store");
        String host = System.getenv().getOrDefault("MONGODB_HOST", "localhost");
        String username = System.getenv().getOrDefault("MONGODB_USER", "admin");
        String password = System.getenv().getOrDefault("MONGODB_PASS", "mypass");
        String connectURL = String.format(
                "mongodb://%s:%s@%s/%s",
                username,
                password,
                host,
                dbname
        );
        MongoClient mongoClient = new MongoClient(
                new MongoClientURI(connectURL)
        );
        DB db = mongoClient.getDB("mongodb_store");
        BasicDBObject removeQuery = new BasicDBObject();
        db.getCollection(tableName).remove(removeQuery);
        mongoClient.close();
    }
}
