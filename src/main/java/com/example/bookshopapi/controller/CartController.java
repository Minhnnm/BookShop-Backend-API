package com.example.bookshopapi.controller;

import com.example.bookshopapi.entity.CartItem;
import com.example.bookshopapi.service.CartItemService;
import com.example.bookshopapi.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemService cartItemService;
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("add-product/{product_id}")
    public ResponseEntity<?> addItemToCart(@PathVariable("product_id") UUID productId){
        return ResponseEntity.ok(cartService.addItemToCart(productId));
    }
}
