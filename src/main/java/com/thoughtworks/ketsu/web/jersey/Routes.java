package com.thoughtworks.ketsu.web.jersey;



import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.product.Product;
import com.thoughtworks.ketsu.domain.user.User;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class Routes {

    private final String baseUri;

    public Routes(UriInfo uriInfo) {
        baseUri = uriInfo.getBaseUri().toASCIIString();
    }

    public URI productUri(Product product){return URI.create("/products/" + product.getId());}

    public URI productUri(String productId){return URI.create("/products/" + productId);}


    public URI userUri(User user){return URI.create("/users/" + user.getId());}

    public URI orderUri(Order order){return URI.create("/users/" + order.getUserId() + "/orders/" + order.getId());}

    public URI paymentUri(String userId, String orderId){return URI.create("/users/" + userId + "/orders/" + orderId + "/payment");}


}
