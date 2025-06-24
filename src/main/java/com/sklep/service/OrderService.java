package com.sklep.service;

import com.sklep.model.Order;
import com.sklep.model.Product;

public interface OrderService {
    Order getOrCreateCart(Long userId);
    Order addToCart(Long userId, Product product, int quantity);
    Order removeFromCart(Long userId, Long productId);
}
