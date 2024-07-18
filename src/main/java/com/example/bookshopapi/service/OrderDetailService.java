package com.example.bookshopapi.service;

import com.example.bookshopapi.entity.CartItem;
import com.example.bookshopapi.entity.Order;

import java.util.List;

public interface OrderDetailService {
    void saveOrderDetail(Order order, List<CartItem> cartItems);
}
