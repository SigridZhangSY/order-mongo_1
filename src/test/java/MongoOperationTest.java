import com.mongodb.*;
import org.junit.Before;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.Date;

public class MongoOperationTest {

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




    @Test
    public void should_insert_and_find() throws Exception{

        MongoOperation mongoOperation = new MongoOperation();
        mongoOperation.insert(db);
        mongoClient.close();
    }
}
