package com.thoughtworks.ketsu.web;


import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.domain.user.UserRepository;
import com.thoughtworks.ketsu.web.exception.InvalidParameterException;
import com.thoughtworks.ketsu.web.jersey.Routes;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("users")
public class UsersApi {

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(Map<String, Object> info,
                               @Context Routes routes,
                               @Context UserRepository userRepository){
        List<String> list = new ArrayList<>();
        if (info.getOrDefault("name", "").toString().isEmpty())
            list.add("name");
        if (list.size() > 0)
            throw new InvalidParameterException(list);
        User user = userRepository.createUser(info).get();
        return Response.created(routes.userUri(user)).build();
    }


    @Path("{userId}")
    public UserApi getUser(@PathParam("userId") String id,
                        @Context UserRepository userRepository){
        return new UserApi(userRepository.findById(id).orElseThrow(() -> new NotFoundException("can not find user by id.")));
    }
}
