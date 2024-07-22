package com.example.bookshopapi.controller;

import com.example.bookshopapi.entity.CartItem;
import com.example.bookshopapi.service.CartItemService;
import com.example.bookshopapi.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> addItemToCart(@PathVariable("product_id") UUID productId) {
        return ResponseEntity.ok(cartService.addItemToCart(productId));
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping("")
    public ResponseEntity<?> getProductsInCart(@RequestParam(name = "page", defaultValue = "1") int page,
                                               @RequestParam(name = "limit", defaultValue = "1") int limit) {
        return ResponseEntity.ok(cartService.findProductInCart(page, limit));
    }

    @PreAuthorize("hasAnyRole('USER')")
    @DeleteMapping("/empty")
    public ResponseEntity<?> emptyCart() {
        return ResponseEntity.ok(cartService.emptyCart());
    }

    @PreAuthorize("hasAnyRole('USER')")
    @DeleteMapping("/remove-product/{product_id}")
    public ResponseEntity<?> removeProductById(@PathVariable("product_id") UUID productId) {
        return ResponseEntity.ok(cartService.removeProductById(productId));
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping("/change-quantity")
    public ResponseEntity<?> changeQuantity(@RequestParam("cart_item_id") UUID cartItemId,
                                            @RequestParam("quantity") Integer quantity){
        return ResponseEntity.ok(cartService.changeQuantity(cartItemId, quantity));
    }
}
