package com.thoughtworks.ketsu.web;


import com.thoughtworks.ketsu.domain.user.User;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    public Response createOrder(){
        return Response.status(201).build();
    }
}
