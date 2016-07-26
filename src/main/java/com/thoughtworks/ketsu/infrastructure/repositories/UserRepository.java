package com.thoughtworks.ketsu.infrastructure.repositories;

import com.mongodb.*;
import com.thoughtworks.ketsu.domain.user.User;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Optional;

public class UserRepository implements com.thoughtworks.ketsu.domain.user.UserRepository {
    @Inject
    DB db;

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

    @Override
    public Optional<User> findById(String id) {
        ObjectId objectId;
        BasicDBObject searchQuery = new BasicDBObject();
        try{
            objectId = new ObjectId(id);
        } catch (Exception e){
            throw new NotFoundException("can not find user by id");
        }
        searchQuery.put("_id", new ObjectId(id));
        return Optional.ofNullable(new User((BasicDBObject) db.getCollection("users").find(searchQuery).next()));
    }
}
