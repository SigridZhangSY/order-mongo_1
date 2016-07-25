package com.thoughtworks.ketsu.infrastructure.repository;

import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
import com.thoughtworks.ketsu.support.DatabaseTestRunner;
import com.thoughtworks.ketsu.support.TestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@RunWith(DatabaseTestRunner.class)
public class ProductRepositoryTest {

    DB db;
    MongoClient mongoClient;

    @Before
    public void connect() throws Exception{
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
        mongoClient = new MongoClient(
                new MongoClientURI(connectURL)
        );

        db = mongoClient.getDB("mongodb_store");
    }

    @Inject
    ProductRepository productRepository;

    @Test
    public void should_save_product(){
        DBCursor dbCursor = productRepository.save(TestHelper.productMap("apple"), db);
        assertThat(dbCursor.size(), is(1));
    }

    @After
    public void closeConnection(){
        mongoClient.close();
    }
}
