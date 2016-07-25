package com.thoughtworks.ketsu.api;

import com.thoughtworks.ketsu.support.ApiSupport;
import com.thoughtworks.ketsu.support.ApiTestRunner;
import com.thoughtworks.ketsu.support.TestHelper;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.core.Response;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(ApiTestRunner.class)
public class ProductApiTest extends ApiSupport{

    @Test
    public void should_return_201_when_post(){
        Response post = post("products", TestHelper.productMap("apple"));
        assertThat(post.getStatus(), is(201));
        assertThat(Pattern.matches(".*/products/.*", post.getLocation().toASCIIString()), is(true));
    }


}
