package com.sklep.controller;

import com.sklep.service.OrderService;
import com.sklep.service.ProductService;
import com.sklep.model.Order;
import com.sklep.model.Order.OrderStatus;
import com.sklep.model.Product;
import com.sklep.dto.OrderItemRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


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

    @PUT
    @Path("/{userId}/submit")
    public Response submitOrder(@PathParam("userId") Long userId) {
        boolean updated = orderService.updateNewOrderStatus(userId, OrderStatus.PROCESSING);
        if (!updated) {
            return Response.status(Response.Status.NOT_FOUND).entity("Order not found").build();
        }
        return Response.ok().entity("Order submitted").build();
    }

    @PUT
    @Path("/{userId}/cancel")
    public Response cancelOrder(@PathParam("userId") Long userId) {
        boolean updated = orderService.updateNewOrderStatus(userId, OrderStatus.CANCELLED);
        if (!updated) {
            return Response.status(Response.Status.NOT_FOUND).entity("Order not found").build();
        }
        return Response.ok().entity("Order cancelled").build();
    }

    @GET
    @Path("/{userId}/all")
    public Response getAllOrders(@PathParam("userId") Long userId) {
        List<Order> orders = orderService.getAllOrdersByUserId(userId);
        return Response.ok(orders).build();
    }

    @GET
    @Path("/admin")
    public Response getOrdersForAdmin() {
        List<Order> orders = orderService.getOrdersByStatuses(Order.OrderStatus.PROCESSING, Order.OrderStatus.CANCELLED);
        return Response.ok(orders).build();
    }

    @PUT
    @Path("/order/{orderId}/status/{newStatus}")
    public Response updateOrderStatus(@PathParam("orderId") Long orderId, @PathParam("newStatus") String newStatus) {
        OrderStatus status;
        try {
            status = OrderStatus.valueOf(newStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid status").build();
        }

        boolean updated = orderService.updateOrderStatus(orderId, status);
        if (!updated) {
            return Response.status(Response.Status.NOT_FOUND).entity("Order not found").build();
        }
        return Response.ok().entity("Order status updated").build();
    }

    @DELETE
    @Path("/order/{orderId}")
    public Response deleteOrder(@PathParam("orderId") Long orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).entity("Order not found").build();
        }
        if (order.getStatus() != Order.OrderStatus.CANCELLED) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Only CANCELLED orders can be deleted").build();
        }

        // Odjebujemy zwrot produktów do magazynu
        order.getItems().forEach(item -> {
            Product product = productService.getProduct(item.getProduct().getId());
            if (product != null) {
                product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
                productService.updateProduct(product); // Musisz mieć taką metodę w service
            }
        });

        orderService.deleteOrder(orderId);
        return Response.ok().entity("Order deleted and items returned to stock").build();
    }
}