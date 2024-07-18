package com.example.bookshopapi.service;

import com.example.bookshopapi.dto.MessageDto;
import com.example.bookshopapi.entity.CartItem;
import com.example.bookshopapi.entity.Order;
import com.example.bookshopapi.entity.Receiver;
import com.example.bookshopapi.entity.User;

import java.util.List;

public interface EmailService {
    MessageDto sendMailForgotPass(String email);
    void sendMailOrder(Receiver receiver, User user, Order order, List<CartItem> cartItems);
}
