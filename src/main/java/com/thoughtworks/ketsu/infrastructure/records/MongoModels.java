package com.thoughtworks.ketsu.infrastructure.records;

import com.google.inject.AbstractModule;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.net.UnknownHostException;
import java.util.Properties;

public class MongoModels extends AbstractModule {

    @Override
    protected void configure() {
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

        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(
                    new MongoClientURI(connectURL)
            );
        }catch (UnknownHostException e){
            e.printStackTrace();
        }

        DB db = mongoClient.getDB("mongodb_store");
        bind(DB.class).toInstance(db);
    }
}
