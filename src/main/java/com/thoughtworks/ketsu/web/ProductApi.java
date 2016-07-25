package com.thoughtworks.ketsu.web;

import com.mongodb.*;
import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
import com.thoughtworks.ketsu.web.jersey.Routes;
import org.apache.ibatis.annotations.Param;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.UnknownHostException;
import java.util.Map;

@Path("products")
public class ProductApi {


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postProduct(@Context ProductRepository productRepository,
                                @Context Routes routes,
                                Map<String,Object> info) throws UnknownHostException {


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
        MongoClient mongoClient = new MongoClient(
                new MongoClientURI(connectURL)
        );

        DB db = mongoClient.getDB("mongodb_store");

        DBCursor dbCursor = productRepository.save(info, db);
        DBObject obj = dbCursor.next();
        Product product = new Product(obj);
        return Response.created(routes.productUri(product)).build();
    }
}
