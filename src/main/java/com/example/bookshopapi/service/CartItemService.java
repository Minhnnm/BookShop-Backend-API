package com.example.bookshopapi.service;

import com.example.bookshopapi.entity.CartItem;

import java.util.UUID;

public interface CartItemService {
    CartItem getProductInCart(UUID productId);
}
