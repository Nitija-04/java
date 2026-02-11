package com.ecommerce.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;


public class ProductService {
    private final List<Product> products = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    public ProductService() {
        //sample products already entered
        addProduct(new Product(null, "Laptop", "High-performance gaming laptop", 75000.0, 10, "Electronics"));
        addProduct(new Product(null, "Smartphone", "Latest 5G smartphone", 45000.0, 25, "Electronics"));
        addProduct(new Product(null, "Coffee Maker", "Automatic coffee brewing machine", 5500.0, 15, "Home Appliances"));
    }

    /**
     * Add a new product to the store
     * @param product Product to add
     * @return Added product with generated ID
     * @throws IllegalArgumentException if validation fails
     */
    public Product addProduct(Product product) {
        validateProduct(product);

        // Generate new ID
        product.setId(idCounter.getAndIncrement());
        products.add(product);
        return product;
    }

    /**
     * Get a product by ID
     * @param id Product ID
     * @return Optional containing the product if found
     */
    public Optional<Product> getProductById(Long id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    /**
     * Get all products
     * @return List of all products
     */
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    /**
     * Validate product fields
     * @param product Product to validate
     * @throws IllegalArgumentException if validation fails
     */
    private void validateProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (product.getPrice() == null || product.getPrice() < 0) {
            throw new IllegalArgumentException("Product price must be a positive number");
        }
        if (product.getQuantity() == null || product.getQuantity() < 0) {
            throw new IllegalArgumentException("Product quantity must be a non-negative number");
        }
        if (product.getCategory() == null || product.getCategory().trim().isEmpty()) {
            throw new IllegalArgumentException("Product category is required");
        }
    }
}