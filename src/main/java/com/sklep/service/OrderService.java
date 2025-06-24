package com.sklep.service;

import com.sklep.model.Order;
import com.sklep.model.Product;
import com.sklep.model.Order.OrderStatus;

import java.util.List;

public interface OrderService {
    Order getOrCreateCart(Long userId);
    Order addToCart(Long userId, Product product, int quantity);
    boolean updateNewOrderStatus(Long userId, OrderStatus newStatus);
    boolean updateOrderStatus(Long orderId, OrderStatus newStatus);
    List<Order> getAllOrdersByUserId(Long userId);
    boolean deleteOrder(Long orderId);
    List<Order> getOrdersByStatuses(Order.OrderStatus... statuses);
    Order getOrderById(Long orderId);
}
