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
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(DatabaseTestRunner.class)
public class UserTest {

    @Inject
    ProductRepository productRepository;

    @Inject
    UserRepository userRepository;

    @Test
    public void should_create_order() throws ParseException {
        User user = userRepository.createUser(TestHelper.userMap("xxx")).get();
        Product product = productRepository.save(TestHelper.productMap("apple"));
        Optional<Order> order = user.createOrder(TestHelper.orderMap(product.getId()));
        assertThat(order.isPresent(), is(true));
    }

}
