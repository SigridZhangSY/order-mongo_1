package com.thoughtworks.ketsu.web;

import com.mongodb.*;
import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
import com.thoughtworks.ketsu.web.exception.InvalidParameterException;
import com.thoughtworks.ketsu.web.jersey.Routes;
import org.apache.ibatis.annotations.Param;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Path("products")
public class ProductsApi {


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postProduct(@Context ProductRepository productRepository,
                                @Context Routes routes,
                                Map<String,Object> info) throws UnknownHostException {
        List<String> list = new ArrayList<>();
        if(info.getOrDefault("name", "").toString().trim().isEmpty())
            list.add("name");
        if(info.getOrDefault("description", "").toString().trim().isEmpty())
            list.add("description");
        if(info.getOrDefault("price", "").toString().trim().isEmpty())
            list.add("price");
        if (list.size() > 0)
            throw new InvalidParameterException(list);

        Product product = productRepository.save(info);
        return Response.created(routes.productUri(product)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Product> listProducts(@Context ProductRepository productRepository){
        return productRepository.list();
    }

    @GET
    @Path("{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Product findProduct(@PathParam("productId") String id,
                               @Context ProductRepository productRepository){
        Optional<Product> product = productRepository.find(id);
        if (product.isPresent())
            return product.get();
        else
            throw new NotFoundException("product not found");
    }

}
