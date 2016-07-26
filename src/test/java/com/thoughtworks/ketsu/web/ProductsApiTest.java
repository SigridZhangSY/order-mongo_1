package com.thoughtworks.ketsu.web;

import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.domain.product.ProductRepository;
import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.ApiTestRunner;
import com.thoughtworks.ketsu.support.TestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(ApiTestRunner.class)
public class ProductsApiTest extends ApiSupport{

    @Inject
    ProductRepository productRepository;

    @Test
    public void should_return_201_when_post() throws UnknownHostException {
        Response post = post("products", TestHelper.productMap("apple"));
        assertThat(post.getStatus(), is(201));
        assertThat(Pattern.matches(".*/products/.*", post.getLocation().toASCIIString()), is(true));
//        TestHelper.clean("products");
    }

    @Test
    public void should_return_400_when_post_product_with_name_is_empty(){
        Map<String, Object> map = TestHelper.productMap("apple");
        map.remove("name");
        Response post = post("products", map);
        assertThat(post.getStatus(), is(400));
    }


    @Test
    public void should_return_detail_when_list_products() throws UnknownHostException {
        Product product = productRepository.save(TestHelper.productMap("name"));
        Response get = get("products");
        assertThat(get.getStatus(), is(200));
        final List<Map<String, Object>> list = get.readEntity(List.class);
        assertThat(list.size(), is(1));
        assertThat(list.get(0).get("uri"), is("/products/" + product.getId()));

    }

    @Test
    public void should_return_detail_when_find_by_id() throws UnknownHostException {
        Product product = productRepository.save(TestHelper.productMap("apple"));
        Response get = get("products/" + product.getId());
        assertThat(get.getStatus(), is(200));
        final Map<String, Object> map = get.readEntity(Map.class);
        assertThat(map.get("uri").toString(), is("/products/" + product.getId()));
    }

    @Test
    public void should_return_404_when_product_not_exist() throws UnknownHostException {
        Response get = get("products/1");
        assertThat(get.getStatus(), is(404));
    }

}
