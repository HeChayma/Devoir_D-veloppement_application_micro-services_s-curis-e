package com.example.Products_Service.reposotories;

import com.example.Products_Service.entites.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
