package com.sklep.dao;

import com.sklep.model.Order;
import com.sklep.model.Order.OrderStatus;
import com.sklep.model.User;
import com.sklep.model.OrderItem;

import java.util.List;
import java.util.Optional;

public interface OrderDAO {
    Order createOrder(User user);
    List<Order> getAllOrdersByUserId(Long userId);
    void removeOrderItem(OrderItem item);
    void addOrderItem(Order order, OrderItem item);
    Optional<Order> findByUserIdAndStatus(Long userId, OrderStatus status);
    void save(Order order);
    Optional<Order> get(Long orderId);
    void remove(Long orderId);
    List<Order> findByStatuses(List<Order.OrderStatus> statuses);
}

