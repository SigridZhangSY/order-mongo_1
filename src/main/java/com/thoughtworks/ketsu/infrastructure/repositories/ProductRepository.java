package com.thoughtworks.ketsu.infrastructure.repositories;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.thoughtworks.ketsu.domain.product.Product;
import org.bson.types.ObjectId;

import java.util.Map;

public class ProductRepository implements com.thoughtworks.ketsu.domain.product.ProductRepository {
    @Override
    public DBCursor save(Map<String, Object> info, DB db) {
        DBCollection table = db.getCollection("products");
        BasicDBObject document = new BasicDBObject();
        document.put("name", info.get("name"));
        table.insert(document);

        BasicDBObject searchQuery = new BasicDBObject();

        ObjectId id = (ObjectId)document.get( "_id" );
        searchQuery.put("_id", id);

        return table.find(searchQuery);
    }
}
