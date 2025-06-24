package com.sklep.service.impl;

import com.sklep.service.ProductService;
import com.sklep.dao.ProductDAO;
import com.sklep.model.Product;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

import java.util.Collection;

@ApplicationScoped
public class ProductServiceImpl implements ProductService {

    @Inject
    private ProductDAO productDao;

    @Override
    public Collection<Product> getAllProducts() {
        return productDao.getAll();
    }

    @Override
    public Product addProduct(Product product) {
        // tu możesz dorzucić walidacje, logikę biznesową itd.
        return productDao.add(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productDao.delete(id);
    }

    @Override
    public Product getProduct(Long id) {
        return productDao.getById(id);
    }

    @Override
    public Product updateProduct(Product product) {
        Product existing = getProduct(product.getId());
        if (existing == null) {
            throw new NotFoundException("Product not found");
        }
        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setStockQuantity(product.getStockQuantity());

        productDao.update(existing);
        return existing;
    }
}
