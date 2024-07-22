package com.example.bookshopapi.mapper;

import com.example.bookshopapi.dto.order.OrderDto;
import com.example.bookshopapi.entity.Order;
import com.example.bookshopapi.mapper.decorator.OrderDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
@DecoratedWith(OrderDecorator.class)
public interface OrderMapper extends EntityMapper<Order, OrderDto> {
    @Override
    @Mapping(source = "id", target = "order_id")
    @Mapping(source = "createOn", target = "created_on", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(source = "shippedOn", target = "shipped_on", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(source = "totalAmount", target = "order_amount")
    @Mapping(source = "receiverName", target = "receiver_name")
    @Mapping(source = "receiverPhone", target = "receiver_phone")
    OrderDto toDto(Order entity);
}
