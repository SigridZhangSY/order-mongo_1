package com.thoughtworks.ketsu.infrastructure.repository;

import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.domain.user.UserRepository;
import com.thoughtworks.ketsu.support.DatabaseTestRunner;
import com.thoughtworks.ketsu.support.TestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(DatabaseTestRunner.class)
public class UserTest {

    @Inject
    ProductRepository productRepository;

    @Inject
    UserRepository userRepository;

    @Test
    public void should_create_order()  {
        User user = userRepository.createUser(TestHelper.userMap("xxx")).get();
        Product product = productRepository.save(TestHelper.productMap("apple"));
        Optional<Order> order = user.createOrder(TestHelper.orderMap(product.getId()));
        assertThat(order.isPresent(), is(true));
    }

    @Test
    public void should_list_orders_for_user() {
        User user = userRepository.createUser(TestHelper.userMap("xxx")).get();
        Product product = productRepository.save(TestHelper.productMap("apple"));
        Order order = user.createOrder(TestHelper.orderMap(product.getId())).get();
        List<Order> orderList = user.listOrders();
        assertThat(orderList.size(), is(1));
    }

    @Test
    public void should_find_order_by_id_for_user(){
        User user = userRepository.createUser(TestHelper.userMap("xxx")).get();
        Product product = productRepository.save(TestHelper.productMap("apple"));
        Order order = user.createOrder(TestHelper.orderMap(product.getId())).get();
        Optional<Order> order_res = user.findOrder(order.getId());
        assertThat(order_res.isPresent(), is(true));
    }

    @Test
    public void should_create_payment_for_order(){
        User user = userRepository.createUser(TestHelper.userMap("xxx")).get();
        Product product = productRepository.save(TestHelper.productMap("apple"));
        Order order = user.createOrder(TestHelper.orderMap(product.getId())).get();
        user.createPaymentForOrder(TestHelper.paymentMap(), order.getId());
        order = user.findOrder(order.getId()).get();
        assertNotNull(order.getPayment());
    }

}
