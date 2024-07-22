package com.example.bookshopapi.mapper;

import com.example.bookshopapi.dto.cart.CartItemDto;
import com.example.bookshopapi.entity.CartItem;
import com.example.bookshopapi.mapper.decorator.CartItemDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
@DecoratedWith(CartItemDecorator.class)
public interface CartItemMapper extends EntityMapper<CartItem, CartItemDto> {
    @Override
    @Mapping(source = "id", target = "item_id")
    @Mapping(source = "addOn", target = "added_on", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(source = "updateOn", target = "update_on", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(source = "quantity", target = "quantity_in_cart")
    CartItemDto toDto(CartItem entity);
}
