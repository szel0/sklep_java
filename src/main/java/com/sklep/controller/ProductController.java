package com.sklep.controller;

import com.sklep.model.Product;
import com.sklep.service.ProductService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.Collection;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductController {

    @Inject
    private ProductService productService;

    @GET
    public Collection<Product> getAll() {
        return productService.getAllProducts();
    }

    @POST
    public Product add(Product product) {
        return productService.addProduct(product);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") Long id) {
        productService.deleteProduct(id);
    }
}
