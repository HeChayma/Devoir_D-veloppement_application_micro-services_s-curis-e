package com.example.Orders_Service.controller;

import com.example.Orders_Service.entities.Order;
import com.example.Orders_Service.entities.OrderItem;
import com.example.Orders_Service.reposotories.OrderRepository;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class ControllerOrder {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    @org.springframework.beans.factory.annotation.Value("${products.service.url:http://localhost:8083}")
    private String productsServiceUrl;

    public ControllerOrder(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/my-orders")
    @PreAuthorize("isAuthenticated()")
    public List<Order> getMyOrders(org.springframework.security.core.Authentication authentication) {
        String customerId = authentication.getName(); // In Keycloak, getName() usually returns the user ID (sub) or
                                                      // username
        return orderRepository.findByCustomerId(customerId);
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public Order createOrder(@RequestBody Order order,
            org.springframework.security.core.Authentication authentication) {
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setCustomerId(authentication.getName());

        // Calculate Total Amount
        BigDecimal total = BigDecimal.ZERO;
        if (order.getOrderItems() != null) {
            for (OrderItem item : order.getOrderItems()) {
                if (item.getPrice() != null) {
                    total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
                }
            }
        }
        order.setTotalAmount(total);

        // Extract Username from JWT
        if (authentication instanceof org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken) {
            org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken jwtAuth = (org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken) authentication;
            String username = (String) jwtAuth.getTokenAttributes().get("preferred_username");
            order.setCustomerName(username != null ? username : authentication.getName());
        } else {
            order.setCustomerName(authentication.getName());
        }

        return orderRepository.save(order);
    }

    @PutMapping("/{id}/validate")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Order validateOrder(@PathVariable Long id, @RequestHeader(name = "Authorization") String token) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));

        if ("VALIDATED".equals(order.getStatus())) {
            throw new RuntimeException("Order already validated");
        }

        BigDecimal total = BigDecimal.ZERO;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        for (OrderItem item : order.getOrderItems()) {
            // Call Products-Service (Configurable URL)
            String url = productsServiceUrl + "/products/reduce-stock/" + item.getProductId() + "?quantity="
                    + item.getQuantity();

            try {
                restTemplate.exchange(url, HttpMethod.PUT, entity, Void.class);
            } catch (Exception e) {
                throw new RuntimeException(
                        "Failed to reduce stock for product " + item.getProductId() + ": " + e.getMessage());
            }

            if (item.getPrice() != null) {
                total = total.add(item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            }
        }

        order.setTotalAmount(total);
        order.setStatus("VALIDATED");
        return orderRepository.save(order);
    }
}
