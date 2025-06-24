package com.sklep.dao.impl;

import com.sklep.model.Product;
import jakarta.enterprise.context.ApplicationScoped;
import com.sklep.dao.ProductDAO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class ProductDAOImpl implements ProductDAO {

    private final Map<Long, Product> produkty = new HashMap<>();
    private long currentId = 1;

    @Override
    public Collection<Product> getAll() {
        return produkty.values();
    }

    @Override
    public Product add(Product product) {
        product.setId(currentId++);
        produkty.put(product.getId(), product);
        return product;
    }

    @Override
    public void delete(Long id) {
        produkty.remove(id);
    }

    @Override
    public Product getById(Long id) {
        return produkty.get(id);
    }

    @Override
    public void update(Product product) {
        if (produkty.containsKey(product.getId())) {
            produkty.put(product.getId(), product);
        }
    }
}
