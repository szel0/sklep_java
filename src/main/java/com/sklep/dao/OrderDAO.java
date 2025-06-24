package com.sklep.dao;

import com.sklep.model.Order;
import com.sklep.model.User;
import com.sklep.model.OrderItem;
import java.util.Optional;

public interface OrderDAO {
    Order createOrder(User user);
    Optional<Order> getCartByUserId(Long userId);
    void removeOrderItem(OrderItem item);
    void addOrderItem(Order order, OrderItem item);
}

