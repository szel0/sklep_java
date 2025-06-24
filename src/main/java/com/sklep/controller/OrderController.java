package com.sklep.controller;

import com.sklep.service.OrderService;
import com.sklep.service.ProductService;
import com.sklep.model.Order;
import com.sklep.model.Product;
import com.sklep.dto.OrderItemRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderController {

    @Inject
    private OrderService orderService;

    @Inject
    private ProductService productService;

    @GET
    @Path("/{userId}")
    public Response getCart(@PathParam("userId") Long userId) {
        Order cart = orderService.getOrCreateCart(userId);
        return Response.ok(cart).build();
    }

    @POST
    @Path("/{userId}/add")
    public Response addToCart(@PathParam("userId") Long userId, OrderItemRequest req) {
        Product product = productService.getProduct(req.getProductId());
        if (product == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Product not found").build();
        }
        Order updated = orderService.addToCart(userId, product, req.getQuantity());
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{userId}/remove/{productId}")
    public Response removeFromCart(@PathParam("userId") Long userId, @PathParam("productId") Long productId) {
        Order updated = orderService.removeFromCart(userId, productId);
        return Response.ok(updated).build();
    }
}