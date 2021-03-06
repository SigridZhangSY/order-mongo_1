package com.thoughtworks.ketsu.web;


import com.thoughtworks.ketsu.domain.order.Order;
import com.thoughtworks.ketsu.domain.order.Payment;
import com.thoughtworks.ketsu.domain.user.User;
import com.thoughtworks.ketsu.web.exception.InvalidParameterException;
import com.thoughtworks.ketsu.web.jersey.Routes;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserApi {
    private User user;


    public UserApi(User user) {
        this.user = user;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public User findUser(){return user;}

    @POST
    @Path("orders")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrder(Map<String, Object> info,
                                @Context Routes routes) throws ParseException {
        List<String> list = new ArrayList<>();
        if (info.getOrDefault("name", "").toString().trim().isEmpty())
            list.add("name");
        if (info.getOrDefault("phone", "").toString().trim().isEmpty())
            list.add("phone");
        if (info.getOrDefault("address", "").toString().trim().isEmpty())
            list.add("address");
        if (info.getOrDefault("order_items", "").toString().trim().isEmpty())
            list.add("order_items");
        else {
            List<Map<String, Object>> items = (List<Map<String, Object>>) info.get("order_items");
            for(Map<String, Object> item : items){
                if (item.getOrDefault("product_id", "").toString().trim().isEmpty())
                    list.add("product_id");
                if (item.getOrDefault("quantity", "").toString().trim().isEmpty())
                    list.add("quantity");
            }
        }
        if (list.size() > 0)
            throw new InvalidParameterException(list);

        Order order = user.createOrder(info).get();
        return Response.created(routes.orderUri(order)).build();
    }

    @GET
    @Path("orders")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> listOrder(){
        return user.listOrders();
    }

    @GET
    @Path("orders/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Order findOrderById(@PathParam("orderId") String orderId){
        return user.findOrder(orderId).orElseThrow(() -> new NotFoundException("can not find order by id."));
    }

    @POST
    @Path("orders/{orderId}/payment")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrder(@Context Routes routes,
                                @PathParam("orderId") String orderId,
                                Map<String, Object> info){

        System.out.println("get it");
        List<String> list = new ArrayList<>();
        if (info.getOrDefault("pay_type", "").toString().trim().isEmpty())
            list.add("pay_type");
        if (info.getOrDefault("amount", "").toString().trim().isEmpty())
            list.add("amount");

        if (list.size() > 0)
            throw new InvalidParameterException(list);

        user.createPaymentForOrder(info, orderId);
        return Response.created(routes.paymentUri(user.getId(), orderId)).build();
    }

    @GET
    @Path("orders/{orderId}/payment")
    @Produces(MediaType.APPLICATION_JSON)
    public Payment findOrder(@PathParam("orderId") String orderId){
        Payment payment = user.findOrder(orderId).get().getPayment();
        if (payment == null)
            throw new NotFoundException("can not find payment.");
        else
            return payment;
    }
}
