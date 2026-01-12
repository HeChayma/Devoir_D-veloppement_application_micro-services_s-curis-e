package com.example.Products_Service.controller;

import com.example.Products_Service.entites.Product;
import com.example.Products_Service.reposotories.ProductRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ControllerProduct {

    private final ProductRepository productRepository;

    public ControllerProduct(ProductRepository productRepository) {
        System.out.println("DEBUG: ControllerProduct instantiated!");
        this.productRepository = productRepository;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());
        product.setQuantityInStock(productDetails.getQuantityInStock());
        return productRepository.save(product);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }

    @PutMapping("/reduce-stock/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Product reduceStock(@PathVariable Long id, @RequestParam int quantity) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        if (product.getQuantityInStock() < quantity) {
            throw new RuntimeException("Not enough stock for product: " + product.getName());
        }
        product.setQuantityInStock(product.getQuantityInStock() - quantity);
        return productRepository.save(product);
    }
}
