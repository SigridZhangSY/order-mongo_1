package com.thoughtworks.ketsu.api;

import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.ApiTestRunner;
import com.thoughtworks.ketsu.support.TestHelper;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(ApiTestRunner.class)
public class ProductApiTest extends ApiSupport{

    @Inject
    ProductRepository productRepository;

    @Test
    public void should_return_201_when_post() throws UnknownHostException {
        Response post = post("products", TestHelper.productMap("apple"));
        assertThat(post.getStatus(), is(201));
        assertThat(Pattern.matches(".*/products/.*", post.getLocation().toASCIIString()), is(true));
        TestHelper.clean("products");
    }

    @Test
    public void should_return_400_when_post_product_with_name_is_empty(){
        Map<String, Object> map = TestHelper.productMap("apple");
        map.remove("name");
        Response post = post("products", map);
        assertThat(post.getStatus(), is(400));
    }


    @Test
    public void should_return_200_when_list_products(){
        Response get = get("products");
        assertThat(get.getStatus(), is(200));
    }

    @Test
    public void should_find_by_id(){
        Product product = productRepository.save(TestHelper.productMap("apple"));
        Response get = get("products/" + product.getId());
        assertThat(get.getStatus(), is(200));
    }


}
