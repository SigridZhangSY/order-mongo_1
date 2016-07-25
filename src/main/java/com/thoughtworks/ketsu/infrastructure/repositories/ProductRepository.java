package com.thoughtworks.ketsu.infrastructure.repositories;

import com.mongodb.*;
import com.thoughtworks.ketsu.domain.product.Product;
import org.bson.types.ObjectId;

import javax.ws.rs.NotFoundException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public Optional<Product> find(String id) {
        ObjectId objectId;
        DBCollection table = db.getCollection("products");
        BasicDBObject searchQuery = new BasicDBObject();
        try{
            objectId = new ObjectId(id);
        } catch (Exception e){
            throw new NotFoundException("product not found");
        }
        searchQuery.put("_id", objectId);
        DBCursor cursor = table.find(searchQuery);
        if(cursor.hasNext()) {
            Product product = new Product((BasicDBObject) cursor.next());
            return Optional.of(product);
        }else
            return Optional.ofNullable(null);
    }


}
