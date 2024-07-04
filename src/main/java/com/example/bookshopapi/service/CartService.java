package com.example.bookshopapi.service;

import com.example.bookshopapi.dto.cart.CartItemDto;

import java.util.List;

public interface CartService {
    List<CartItemDto> addItemToCart(int productId);
}
