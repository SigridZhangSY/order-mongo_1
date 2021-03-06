package com.thoughtworks.ketsu.infrastructure.repository;


import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.domain.user.UserRepository;
import com.thoughtworks.ketsu.support.DatabaseTestRunner;
import com.thoughtworks.ketsu.support.TestHelper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.net.UnknownHostException;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(DatabaseTestRunner.class)
public class UserRepositoryTest {
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
    UserRepository userRepository;

    @Test
    public void should_save_user() throws UnknownHostException {
        Optional<User> user = userRepository.createUser(TestHelper.userMap("xxx"));
        assertThat(user.isPresent(), is(true));
    }

    @Test
    public void should_find_user_by_id(){
        User user = userRepository.createUser(TestHelper.userMap("xxx")).get();
        Optional<User> fetch = userRepository.findById(user.getId());
        assertThat(fetch.isPresent(), is(true));
    }
}
