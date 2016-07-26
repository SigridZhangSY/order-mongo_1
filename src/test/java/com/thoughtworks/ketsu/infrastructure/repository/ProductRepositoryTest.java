package com.thoughtworks.ketsu.infrastructure.repository;

import com.mongodb.*;
import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
import com.thoughtworks.ketsu.support.DatabaseTestRunner;
import com.thoughtworks.ketsu.support.TestHelper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.net.UnknownHostException;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@RunWith(DatabaseTestRunner.class)
public class ProductRepositoryTest {


    @Inject
    ProductRepository productRepository;

    @Inject
    DB db;

    @Test
    public void should_save_product() throws UnknownHostException {
        Product product = productRepository.save(TestHelper.productMap("apple"));
        assertThat(product.getName(), is("apple"));
        BasicDBObject removeQuery = new BasicDBObject();
        db.getCollection("products").remove(removeQuery);
//        TestHelper.clean("products");
    }

    @Test
    public void should_list_products() throws UnknownHostException {
        Product product = productRepository.save(TestHelper.productMap("apple"));
        List<Product> list = productRepository.list();
        assertThat(list.size(), is(1));
//        TestHelper.clean("products");
    }

    @Test
    public void should_find_product_by_id() throws UnknownHostException {
        Product product = productRepository.save(TestHelper.productMap("apple"));
        Product product_res = productRepository.find(product.getId()).get();
        assertThat(product_res.getId(), is(product.getId()));
//        TestHelper.clean("products");
    }


}
