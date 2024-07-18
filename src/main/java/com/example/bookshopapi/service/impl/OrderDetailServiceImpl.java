package com.example.bookshopapi.service.impl;

import com.example.bookshopapi.entity.CartItem;
import com.example.bookshopapi.entity.Order;
import com.example.bookshopapi.entity.OrderDetail;
import com.example.bookshopapi.repository.OrderDetailRepository;
import com.example.bookshopapi.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public void saveOrderDetail(Order order, List<CartItem> cartItems) {
        List<OrderDetail> orderDetails=new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setBookName(cartItem.getProduct().getName());
            orderDetail.setUniCost(cartItem.getProduct().getDiscountedPrice());
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetails.add(orderDetail);
        }
        orderDetailRepository.saveAll(orderDetails);
    }
}
