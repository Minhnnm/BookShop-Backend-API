package com.example.bookshopapi.controller;

import com.example.bookshopapi.dto.order.OrderRequest;
import com.example.bookshopapi.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("")
    public ResponseEntity<?> getOrderByUser() {
        return ResponseEntity.ok(orderService.getOrderByUser());
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.createOrder(orderRequest));
    }
}
