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

    public ProductDAOImpl() {
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();

        product1.setName("prod1");
        product2.setName("prod2");
        product3.setName("prod3");

        product1.setDescription("prod1");
        product2.setDescription("prod2");
        product3.setDescription("prod3");

        product1.setPrice(10.0);
        product2.setPrice(20.0);
        product3.setPrice(30.0);

        product1.setStockQuantity(10);
        product2.setStockQuantity(20);
        product3.setStockQuantity(30);

        product1.setId(currentId++);
        product2.setId(currentId++);
        product3.setId(currentId++);

        produkty.put(product1.getId(), product1);
        produkty.put(product2.getId(), product2);
        produkty.put(product3.getId(), product3);
    }

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
