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
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(ApiTestRunner.class)
public class UsersApiTest extends ApiSupport {

    @Inject
    UserRepository userRepository;

    @Test
    public void should_return_201_when_post_user_with_specified_parameter() {
        Response post = post("users", TestHelper.userMap("xxx"));
        assertThat(post.getStatus(), is(201));
        assertThat(Pattern.matches(".*/users/.*", post.getLocation().toASCIIString()), is(true));
    }

    @Test
    public void should_return_400_when_post_user_with_invalid_parameter(){
        Response post = post("users", new HashMap<String, Object>());
        assertThat(post.getStatus(), is(400));
        final List<Map<String, Object>> list = post.readEntity(List.class);
        assertThat(list.size(), is(1));
    }


    @Test
    public void should_return_detail_when_find_user(){
        User user = userRepository.createUser(TestHelper.userMap("xxx")).get();
        Response get = get("users/" + user.getId());
        assertThat(get.getStatus(), is(200));
        final Map<String, Object> fetch = get.readEntity(Map.class);
        assertThat(fetch.get("uri"), is("/users/" + user.getId()));
    }

}
