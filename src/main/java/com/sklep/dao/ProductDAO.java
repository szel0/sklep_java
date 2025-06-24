package com.sklep.dao;

import com.sklep.model.Product;
import java.util.Collection;

public interface ProductDAO {
    Collection<Product> getAll();
    Product add(Product product);
    void delete(Long id);
    Product getById(Long id);
    void update(Product product);
}
