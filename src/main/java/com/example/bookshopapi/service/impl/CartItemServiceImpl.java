package com.example.bookshopapi.service.impl;

import com.example.bookshopapi.entity.CartItem;
import com.example.bookshopapi.entity.User;
import com.example.bookshopapi.exception.BadRequestException;
import com.example.bookshopapi.exception.NotFoundException;
import com.example.bookshopapi.repository.CartItemRepository;
import com.example.bookshopapi.repository.CategoryRepository;
import com.example.bookshopapi.service.CartItemService;
import com.example.bookshopapi.util.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CurrentUserUtil currentUserUtil;

    @Override
    public CartItem getProductInCart(UUID productId) {
        User currentUser = currentUserUtil.getCurrentUser();
        CartItem cartItem = cartItemRepository.findByProductIdAndCartUserId(productId, currentUser.getId()).orElseThrow(
                () -> new NotFoundException("Can not find cartitem")
        );
        return cartItem;
    }
}
