package com.example.bookshopapi.controller;

import com.example.bookshopapi.entity.CartItem;
import com.example.bookshopapi.service.CartItemService;
import com.example.bookshopapi.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemService cartItemService;
}
