package com.example.Products_Service;

import com.example.Products_Service.entites.Product;
import com.example.Products_Service.reposotories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@SpringBootApplication
@org.springframework.context.annotation.ComponentScan(basePackages = { "com.example.Products_Service" })
public class ProductsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductsServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(ProductRepository productRepository) {
		return args -> {
			productRepository.save(new Product("Laptop", "Dell XPS 15", new BigDecimal("1200.00"), 10));
			productRepository.save(new Product("Smartphone", "iPhone 15", new BigDecimal("999.99"), 20));
			productRepository.save(new Product("Headphones", "Sony WH-1000XM5", new BigDecimal("349.99"), 15));

			productRepository.findAll().forEach(p -> {
				System.out.println(p.getName());
			});
		};
	}

}
