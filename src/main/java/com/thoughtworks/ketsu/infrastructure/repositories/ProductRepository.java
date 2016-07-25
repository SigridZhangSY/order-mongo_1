package com.thoughtworks.ketsu.infrastructure.repositories;

import com.mongodb.*;
import com.thoughtworks.ketsu.domain.product.Product;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductRepository implements com.thoughtworks.ketsu.domain.product.ProductRepository {
    private DB db;
    private MongoClient mongoClient;

    public ProductRepository() throws UnknownHostException {
        mongoClient = new DataBaseConnect().getMongoClient();
        this.db = mongoClient.getDB("mongodb_store");
    }

    @Override
    public Product save(Map<String, Object> info) {
        DBCollection table = db.getCollection("products");
        BasicDBObject document = new BasicDBObject();
        document.put("name", info.get("name"));
        document.put("description", info.get("description"));
        document.put("price", info.get("price"));
        table.insert(document);

        BasicDBObject searchQuery = new BasicDBObject();
        ObjectId id = (ObjectId)document.get( "_id" );
        searchQuery.put("_id", id);
        DBObject obj = table.find(searchQuery).next();
        Product product = new Product((BasicDBObject) obj);

//        new DataBaseConnect().close(mongoClient);


        return product;
    }

    @Override
    public List<Product> list() {
        DBCollection table = db.getCollection("products");
        DBCursor cursor = table.find();
        List<Product> productList = new ArrayList<>();
        while (cursor.hasNext()){
            productList.add(new Product((BasicDBObject) cursor.next()));
        }
        return productList;
    }

    @Override
    public Product find(String id) {
        DBCollection table = db.getCollection("products");
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("_id", new ObjectId(id));
        DBObject obj = table.find(searchQuery).next();
        Product product = new Product((BasicDBObject) obj);
        return product;
    }


}
