package com.thoughtworks.ketsu.infrastructure.repositories;

import com.mongodb.*;
import com.thoughtworks.ketsu.domain.user.User;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.Map;
import java.util.Optional;

public class UserRepository implements com.thoughtworks.ketsu.domain.user.UserRepository {
    private DB db;
    private MongoClient mongoClient;

    public UserRepository() throws UnknownHostException {
        mongoClient = new DataBaseConnect().getMongoClient();
        this.db = mongoClient.getDB("mongodb_store");
    }

    @Override
    public Optional<User> createUser(Map<String, Object> info) {
        DBCollection table = db.getCollection("users");
        BasicDBObject document = new BasicDBObject();
        document.put("name", info.get("name"));
        table.insert(document);

        BasicDBObject searchQuery = new BasicDBObject();
        ObjectId id = (ObjectId)document.get( "_id" );
        searchQuery.put("_id", id);
        DBObject obj = table.find(searchQuery).next();
        return Optional.ofNullable(new User((BasicDBObject) obj));
    }
}
