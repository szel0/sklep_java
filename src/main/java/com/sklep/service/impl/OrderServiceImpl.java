package com.sklep.service.impl;

import com.sklep.dao.OrderDAO;
import com.sklep.dao.UserDAO;
import com.sklep.model.Order;
import com.sklep.model.Order.OrderStatus;
import com.sklep.model.OrderItem;
import com.sklep.model.Product;
import com.sklep.service.OrderService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;

@ApplicationScoped
public class OrderServiceImpl implements OrderService {

    @Inject
    private OrderDAO orderDAO;

    @Inject
    private UserDAO userDAO;

    @Override
    public Order getOrCreateCart(Long userId) {
        return orderDAO.findByUserIdAndStatus(userId, OrderStatus.NEW)
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
        item.setUnitPrice(product.getPrice());
        orderDAO.addOrderItem(cart, item);
        return cart;
    }

    @Override
    public boolean updateNewOrderStatus(Long userId, OrderStatus newStatus) {
        Optional<Order> optionalOrder = orderDAO.findByUserIdAndStatus(userId, OrderStatus.NEW);
        if (optionalOrder.isEmpty()) {
            return false;
        }
        Order order = optionalOrder.get();
        order.setStatus(newStatus);
        orderDAO.save(order);

        if (newStatus != OrderStatus.NEW) {
            orderDAO.createOrder(userDAO.findAll().stream()
                    .filter(u -> u.getId() == userId)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("User not found")));
        }
        return true;
    }

    @Override
    public List<Order> getAllOrdersByUserId(Long userId) {
        return orderDAO.getAllOrdersByUserId(userId);
    }

    @Override
    public boolean deleteOrder(Long orderId) {
        Optional<Order> order = orderDAO.get(orderId);
        if (order.isPresent()) {
            orderDAO.remove(orderId);
            return true;
        }
        return false;
    }

    @Override
    public List<Order> getOrdersByStatuses(Order.OrderStatus... statuses) {
        return orderDAO.findByStatuses(Arrays.asList(statuses));
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderDAO.get(orderId).orElse(null);
    }

    @Override
    public boolean updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Optional<Order> optionalOrder = orderDAO.get(orderId);
        if (optionalOrder.isEmpty()) {
            return false;
        }
        Order order = optionalOrder.get();
        order.setStatus(newStatus);
        orderDAO.save(order);
        return true;
    }
}
