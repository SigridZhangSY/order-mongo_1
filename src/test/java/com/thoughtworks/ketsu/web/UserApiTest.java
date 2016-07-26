package com.thoughtworks.ketsu.web;


import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.domain.user.UserRepository;
import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.ApiTestRunner;
import com.thoughtworks.ketsu.support.TestHelper;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(ApiTestRunner.class)
public class UserApiTest extends ApiSupport{

    @Inject
    UserRepository userRepository;

    @Test
    public void should_return_201_when_post_order(){
        User user = userRepository.createUser(TestHelper.userMap("xxx")).get();
        Response post = post("users/" + user.getId() + "/orders", new HashMap<String, Object>());
        assertThat(post.getStatus(), is(201));
    }
}
