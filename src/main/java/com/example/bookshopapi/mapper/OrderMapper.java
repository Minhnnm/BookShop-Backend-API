package com.example.bookshopapi.mapper;

import com.example.bookshopapi.dto.order.OrderDto;
import com.example.bookshopapi.entity.Order;
import com.example.bookshopapi.mapper.decorator.OrderDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@DecoratedWith(OrderDecorator.class)
public interface OrderMapper extends EntityMapper<Order, OrderDto> {
}
