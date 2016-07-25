import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;


public class MongoOperation {


//    public void connect() throws UnknownHostException {
//        String textUri = "mongodb://localhost:27017";
//        MongoClientURI uri = new MongoClientURI(textUri);
//        MongoClient m;
//
//        try {
//            m = new MongoClient(uri);
//            DB db = m.getDB("test");
//            DBCollection table = db.getCollection("user");
//
//            BasicDBObject document = new BasicDBObject();
//            document.put("name", "mkyong");
//            document.put("age", 30);
//            document.put("createdDate", new Date());
//            table.insert(document);
//
//            BasicDBObject searchQuery = new BasicDBObject();
//            searchQuery.put("name", "mkyong");
//
//            DBCursor cursor = table.find(searchQuery);
//
//            while (cursor.hasNext()) {
//                System.out.println(cursor.next());
//            }
//
//
////            List<String> dbs = m.getDatabaseNames();
////            for(String dbname : dbs){
////                System.out.println(dbname);
////            }
//        } catch (UnknownHostException e) {
//            System.err.println("Connection failed: " + e);
//        }
//    }

    public void insert(DB db){
        DBCollection table = db.getCollection("user");

        BasicDBObject document = new BasicDBObject();
        document.put("name", "mkyong");
        document.put("age", 30);
        document.put("createdDate", new Date());
        table.insert(document);

        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("name", "mkyong");

        DBCursor cursor = table.find(searchQuery);

        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }
}
