package com.example.bookshopapi.service;

import com.example.bookshopapi.dto.MessageDto;
import com.example.bookshopapi.dto.order.OrderDto;
import com.example.bookshopapi.dto.order.OrderRequest;
import com.example.bookshopapi.entity.Order;

import java.util.List;

public interface OrderService {
    List<OrderDto> getOrderByUser();
    MessageDto createOrder(OrderRequest orderRequest);
}
