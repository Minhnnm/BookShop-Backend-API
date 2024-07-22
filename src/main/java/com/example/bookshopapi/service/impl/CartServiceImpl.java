package com.example.bookshopapi.service.impl;

import com.example.bookshopapi.dto.MessageDto;
import com.example.bookshopapi.dto.cart.CartItemDto;
import com.example.bookshopapi.entity.Cart;
import com.example.bookshopapi.entity.CartItem;
import com.example.bookshopapi.entity.Product;
import com.example.bookshopapi.entity.User;
import com.example.bookshopapi.exception.ExistedException;
import com.example.bookshopapi.exception.NotFoundException;
import com.example.bookshopapi.mapper.CartItemMapper;
import com.example.bookshopapi.repository.CartItemRepository;
import com.example.bookshopapi.repository.CartRepository;
import com.example.bookshopapi.repository.ProductRepository;
import com.example.bookshopapi.service.CartService;
import com.example.bookshopapi.util.CurrentUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private CartItemMapper cartItemMapper;
    @Autowired
    private CurrentUserUtil currentUserUtil;

    @Override
    public MessageDto addItemToCart(UUID productId) {
        User currentUser = currentUserUtil.getCurrentUser();
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NotFoundException("Can not find product with id: " + productId)
        );
        Cart cart = cartRepository.findByUserId(currentUser.getId());
        Optional<CartItem> cartItemExisted = cartItemRepository.findByProductIdAndCartUserId(productId, currentUser.getId());
        CartItem cartItem;
        if (!cartItemExisted.isPresent()) {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(1);
            cartItem.setAddOn(LocalDateTime.now());
        } else {
            cartItem = cartItemExisted.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }
        cartItemRepository.save(cartItem);
        return new MessageDto("Đã thêm sản phẩm vào giỏ hàng");
    }

    @Override
    public List<CartItemDto> findProductInCart(int page, int limit) {
        User currentUser = currentUserUtil.getCurrentUser();
        PageRequest pageRequest = PageRequest.of(page - 1, limit);
        Page<CartItem> cartItem = cartItemRepository.findCartItemByCartUserIdOrderByUpdateOnDesc(currentUser.getId(), pageRequest);
        return cartItemMapper.toDtos(cartItem.getContent());
    }

    @Override
    public MessageDto emptyCart() {
        User user = currentUserUtil.getCurrentUser();
        List<CartItem> cartItems = cartItemRepository.findCartItemByCartUserId(user.getId());
        cartItemRepository.deleteAll(cartItems);
        return new MessageDto("Đã làm trống giỏ hàng");
    }

    @Override
    public MessageDto removeProductById(UUID productId) {
        User currentUser = currentUserUtil.getCurrentUser();
        CartItem cartItemExisted = cartItemRepository.findByProductIdAndCartUserId(productId, currentUser.getId()).orElseThrow(
                () -> new NotFoundException("Can not find product in cart with id: " + productId)
        );
        cartItemRepository.delete(cartItemExisted);
        return new MessageDto("Đã xóa sản phẩm khỏi giỏ hàng");
    }

    @Override
    public MessageDto changeQuantity(UUID cartItemId, Integer quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(
                () -> new NotFoundException("Can not find cart item id: " + cartItemId)
        );
        cartItem.setQuantity(cartItem.getQuantity() + quantity);
        cartItem.setUpdateOn(LocalDateTime.now());
        cartItemRepository.save(cartItem);
        return new MessageDto("Cập nhật số lượng thành công");
    }
}
