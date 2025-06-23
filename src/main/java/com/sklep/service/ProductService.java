package com.sklep.service;

import com.sklep.model.Product;
import java.util.Collection;

public interface ProductService {
    Collection<Product> getAllProducts();
    Product addProduct(Product product);
    void deleteProduct(Long id);
}
