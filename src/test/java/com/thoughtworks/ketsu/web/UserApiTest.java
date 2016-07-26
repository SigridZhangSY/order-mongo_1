package com.thoughtworks.ketsu.web;


import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
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
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(ApiTestRunner.class)
public class UserApiTest extends ApiSupport{

    @Inject
    UserRepository userRepository;

    @Inject
    ProductRepository productRepository;

    @Test
    public void should_return_201_when_post_order_with_specified_parameter(){
        User user = userRepository.createUser(TestHelper.userMap("xxx")).get();
        Product product = productRepository.save(TestHelper.productMap("apple"));
        Response post = post("users/" + user.getId() + "/orders", TestHelper.orderMap(product.getId()));
        assertThat(post.getStatus(), is(201));
    }

    @Test
    public void should_return_400_when_create_order_with_name_is_empty(){
        User user = userRepository.createUser(TestHelper.userMap("xxx")).get();
        Product product = productRepository.save(TestHelper.productMap("apple"));
        Map<String, Object> map = TestHelper.orderMap(product.getId());
        map.remove("name");
        Response post = post("users/" + user.getId() + "/orders", map);
        assertThat(post.getStatus(), is(400));
        final List<Map<String, Object>> list = post.readEntity(List.class);
        assertThat(list.size(), is(1));
    }

    @Test
    public void should_return_200_when_list_order(){
        User user = userRepository.createUser(TestHelper.userMap("xxx")).get();
        Product product = productRepository.save(TestHelper.productMap("apple"));
        Order order = user.createOrder(TestHelper.orderMap(product.getId())).get();
        Response get = get("users/" + user.getId() + "/orders");
        assertThat(get.getStatus(), is(200));
        final List<Map<String, Object>> list = get.readEntity(List.class);
        assertThat(list.size(), is(1));
    }

    @Test
    public void should_return_detail_when_find_order_by_id(){
        User user = userRepository.createUser(TestHelper.userMap("xxx")).get();
        Product product = productRepository.save(TestHelper.productMap("apple"));
        Order order = user.createOrder(TestHelper.orderMap(product.getId())).get();
        Response get = get("users/" + user.getId() + "/orders/" + order.getId());
        assertThat(get.getStatus(), is(200));
        final Map<String, Object> map = get.readEntity(Map.class);
        assertThat(map.get("uri"), is("/users/" + user.getId() + "/orders/" + order.getId()));
    }

    @Test
    public void should_return_404_when_order_not_exist(){
        User user = userRepository.createUser(TestHelper.userMap("xxx")).get();
        Response get = get("users/" + user.getId() + "/orders/1");
        assertThat(get.getStatus(), is(404));

    }
}
