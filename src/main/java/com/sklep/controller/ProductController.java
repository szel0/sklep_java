package com.sklep.controller;

import com.sklep.model.Product;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final Map<Long, Product> produkty = new HashMap<>();
    private long currentId = 1;

    @GetMapping
    public Collection<Product> getAll() {
        return produkty.values();
    }

    @PostMapping
    public Product add(@RequestBody Product produkt) {
        produkt.setId(currentId++);
        produkty.put(produkt.getId(), produkt);
        return produkt;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        produkty.remove(id);
    }
}
