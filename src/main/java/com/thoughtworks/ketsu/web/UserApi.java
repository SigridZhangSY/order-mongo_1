package com.thoughtworks.ketsu.web;


import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.web.jersey.Routes;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.Map;

public class UserApi {
    private User user;


    public UserApi(User user) {
        this.user = user;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User findUser(){return user;}

    @POST
    @Path("orders")
    public Response createOrder(Map<String, Object> info,
                                @Context Routes routes) throws ParseException {
        Order order = user.createOrder(info).get();
        return Response.created(routes.orderUri(order)).build();
    }
}
