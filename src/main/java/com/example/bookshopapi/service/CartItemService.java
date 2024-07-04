package com.example.bookshopapi.service;

import com.example.bookshopapi.entity.CartItem;

public interface CartItemService {
    CartItem getProductInCart(int productId);
}
