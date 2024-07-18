package com.example.bookshopapi.mapper.decorator;

import com.example.bookshopapi.dto.order.OrderDto;
import com.example.bookshopapi.entity.Order;
import com.example.bookshopapi.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

public abstract class OrderDecorator implements OrderMapper {
    @Autowired
    @Qualifier("delegate")
    private OrderMapper delegate;

    @Override
    public OrderDto toDto(Order entity) {
        OrderDto orderDto = delegate.toDto(entity);
        if (entity.getOrderStatus() != null) {
            orderDto.setOrder_status(entity.getOrderStatus().getStatus());
        }
        if (entity.getShipping() != null) {
            orderDto.setShipping_type(entity.getShipping().getShippingType());
            orderDto.setShipping_cost(entity.getShipping().getShippingCost());
        }
        return orderDto;
    }

    @Override
    public List<OrderDto> toDtos(List<Order> entities) {
        if (entities == null) {
            return null;
        }
        List<OrderDto> orderDtos = new ArrayList<>();
        for (Order order : entities) {
            orderDtos.add(delegate.toDto(order));
        }
        return orderDtos;
    }
}
