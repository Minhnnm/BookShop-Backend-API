package com.example.bookshopapi.service;

import com.example.bookshopapi.dto.cart.CartItemDto;

import java.util.List;
import java.util.UUID;

public interface CartService {
    List<CartItemDto> addItemToCart(UUID productId);
}
