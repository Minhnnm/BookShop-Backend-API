package com.example.bookshopapi.mapper.decorator;

import com.example.bookshopapi.dto.cart.CartItemDto;
import com.example.bookshopapi.entity.CartItem;
import com.example.bookshopapi.entity.Product;
import com.example.bookshopapi.mapper.CartItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class CartItemDecorator implements CartItemMapper {
    @Autowired
    @Qualifier("delegate")
    private CartItemMapper delegate;

    @Override
    public CartItemDto toDto(CartItem entity) {
        CartItemDto cartItemDto = delegate.toDto(entity);
        if (entity.getProduct() != null) {
            Product product = entity.getProduct();
            cartItemDto.setProduct_id(product.getId());
            cartItemDto.setName(product.getName());
            cartItemDto.setSub_total(product.getDiscountedPrice().multiply(new BigDecimal(product.getQuantity())).toString());
            cartItemDto.setQuantity(product.getQuantity());
            cartItemDto.setQuantity_sold(product.getQuantitySold());
            cartItemDto.setPrice(product.getPrice().toString());
            cartItemDto.setDiscounted_price(product.getDiscountedPrice().toString());
            cartItemDto.setImage(product.getImage());
        }
        return cartItemDto;
    }

    @Override
    public List<CartItemDto> toDtos(List<CartItem> entities) {
        if (entities == null) {
            return null;
        }
        List<CartItemDto> cartItems = new ArrayList<>();
        for (CartItem cartItem : entities) {
            cartItems.add(toDto(cartItem));
        }
        return cartItems;
    }
}
