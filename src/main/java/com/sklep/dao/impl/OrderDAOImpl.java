package com.sklep.dao.impl;

import com.sklep.dao.OrderDAO;
import com.sklep.model.Order;
import com.sklep.model.User;
import com.sklep.model.OrderItem;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.*;
import java.time.LocalDateTime;

@ApplicationScoped
public class OrderDAOImpl implements OrderDAO {

    private final Map<Long, Order> orders = new HashMap<>();
    private long currentId = 1;

    @Override
    public Order createOrder(User user) {
        Order order = new Order();
        order.setId(currentId++);
        order.setUser(user);
        order.setStatus(Order.OrderStatus.NEW);
        order.setOrderDate(LocalDateTime.now());
        order.setItems(new ArrayList<>());
        order.setTotalAmount(0.0);
        orders.put(order.getId(), order);
        return order;
    }

    @Override
    public Optional<Order> getCartByUserId(Long userId) {
        return orders.values().stream()
                .filter(o -> o.getUser().getId() == userId && o.getStatus() == Order.OrderStatus.NEW)
                .findFirst();
    }

    @Override
    public void removeOrderItem(OrderItem item) {
        Order order = item.getOrder();
        order.getItems().remove(item);
        order.setTotalAmount(order.getTotalAmount() - item.getUnitPrice() * item.getQuantity());
    }

    @Override
    public void addOrderItem(Order order, OrderItem item) {
        item.setOrder(order);
        order.getItems().add(item);
        order.setTotalAmount(order.getTotalAmount() + item.getUnitPrice() * item.getQuantity());
    }
}
