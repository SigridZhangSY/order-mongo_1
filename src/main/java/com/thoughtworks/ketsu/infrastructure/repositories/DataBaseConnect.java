package com.thoughtworks.ketsu.infrastructure.repositories;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.net.UnknownHostException;

public class DataBaseConnect {
//    private DB db;
    private MongoClient mongoClient;
    String connectURL;

    public DataBaseConnect() {

    String dbname = System.getenv().getOrDefault("MONGODB_DATABASE", "mongodb_store");
    String host = System.getenv().getOrDefault("MONGODB_HOST", "localhost");
    String username = System.getenv().getOrDefault("MONGODB_USER", "admin");
    String password = System.getenv().getOrDefault("MONGODB_PASS", "mypass");
    connectURL = String.format(
            "mongodb://%s:%s@%s/%s",
            username,
            password,
            host,
            dbname
    );
//        db = mongoClient.getDB("mongodb_store");
    }

    public void close(MongoClient client){
        client.close();
    }

    public MongoClient getMongoClient() throws UnknownHostException {
        mongoClient = new MongoClient(
                new MongoClientURI(connectURL)
        );
        return mongoClient;
    }
}
