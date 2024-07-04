package com.example.bookshopapi.service;

import com.example.bookshopapi.dto.MessageDto;

public interface EmailService {
    MessageDto sendMailForgotPass(String email);
}
