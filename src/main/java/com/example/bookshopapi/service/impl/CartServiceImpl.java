package com.example.bookshopapi.service.impl;

import com.example.bookshopapi.dto.cart.CartItemDto;
import com.example.bookshopapi.entity.Cart;
import com.example.bookshopapi.entity.CartItem;
import com.example.bookshopapi.entity.Product;
import com.example.bookshopapi.entity.User;
import com.example.bookshopapi.exception.ExistedException;
import com.example.bookshopapi.exception.NotFoundException;
import com.example.bookshopapi.repository.CartItemRepository;
import com.example.bookshopapi.repository.CartRepository;
import com.example.bookshopapi.repository.ProductRepository;
import com.example.bookshopapi.service.CartService;
import com.example.bookshopapi.util.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CurrentUserUtil currentUserUtil;

    @Override
    public List<CartItemDto> addItemToCart(UUID productId) {
        User currentUser = currentUserUtil.getCurrentUser();
        Product product = productRepository.findById(productId).orElseThrow(
                ()->new NotFoundException("Can not find product with id: " + productId)
        );
        Cart cart=cartRepository.findByUserId(currentUser.getId());
//        Optional<CartItem> cartItemExisted = cartItemRepository.findByProductIdAndCartUserId(productId, currentUser.getId());
//        if (!cartItemExisted.isPresent()) {
//            CartItem cartItem = new CartItem();
//            cartItem.setCart(cart);
//            cartItem.setProduct(product);
//            cartItem.setQuantity(1);
//            cartItem.setAddOn(LocalDateTime.now());
//            cartItemRepository.save(cartItem);
//        } else {
//            cartItemExisted.setQuantity(cartItemExisted.getQuantity() + 1);
//            cartItemService.save(cartItemExisted);
//        }
        return null;
    }
}
