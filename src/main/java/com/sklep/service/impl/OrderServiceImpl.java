package com.sklep.service.impl;

import com.sklep.dao.OrderDAO;
import com.sklep.dao.UserDAO;
import com.sklep.model.Order;
import com.sklep.model.OrderItem;
import com.sklep.model.Product;
import com.sklep.service.OrderService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class OrderServiceImpl implements OrderService {

    @Inject
    private OrderDAO orderDAO;

    @Inject
    private UserDAO userDAO;

    @Override
    public Order getOrCreateCart(Long userId) {
        return orderDAO.getCartByUserId(userId)
                .orElseGet(() -> orderDAO.createOrder(userDAO.findAll().stream()
                        .filter(u -> u.getId() == userId)
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("User not found"))));
    }

    @Override
    public Order addToCart(Long userId, Product product, int quantity) {
        Order cart = getOrCreateCart(userId);
        OrderItem item = new OrderItem();
        item.setProduct(product);
        product.setStockQuantity(product.getStockQuantity() - quantity);
        item.setQuantity(quantity);
        item.setUnitPrice(product.getPrice()); // zakładam, że masz `getPrice()` w Product
        orderDAO.addOrderItem(cart, item);
        return cart;
    }

    @Override
    public Order removeFromCart(Long userId, Long productId) {
        Order cart = getOrCreateCart(userId);
        OrderItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Item not found in cart"));
        orderDAO.removeOrderItem(item);
        return cart;
    }
}
