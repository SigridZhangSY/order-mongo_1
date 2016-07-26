package com.thoughtworks.ketsu.infrastructure.repositories;

import com.google.inject.Injector;
import com.mongodb.*;
import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.order.OrderItem;
import com.thoughtworks.ketsu.domain.product.*;
import com.thoughtworks.ketsu.domain.user.User;
import org.bson.types.ObjectId;

import javax.inject.Inject;
import javax.ws.rs.NotFoundException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class UserRepository implements com.thoughtworks.ketsu.domain.user.UserRepository {
    @Inject
    DB db;

    @Inject
    ProductRepository productRepository;

    @Inject
    Injector injector;

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
        if(obj == null)
            return Optional.of(null);
        else {
            User user = new User((BasicDBObject) obj);
            injector.injectMembers(user);
            return Optional.ofNullable(user);
        }
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
        DBObject obj = db.getCollection("users").find(searchQuery).next();
        if(obj == null)
            return Optional.of(null);
        else {
            User user = new User((BasicDBObject) obj);
            injector.injectMembers(user);
            return Optional.ofNullable(user);
        }
    }


}
