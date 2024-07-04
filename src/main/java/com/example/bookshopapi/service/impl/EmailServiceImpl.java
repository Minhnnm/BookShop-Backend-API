package com.example.bookshopapi.service.impl;

import com.example.bookshopapi.dto.MessageDto;
import com.example.bookshopapi.entity.User;
import com.example.bookshopapi.repository.UserRepository;
import com.example.bookshopapi.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private Executor asyncExecutor;

    public boolean sendEmail(String to, String subject, String text) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true); // Đặt tham số thứ hai là true để cho phép nội dung là HTML

            // Đặt tên người gửi
            String senderName = "BookShop";
            String senderEmail = "bookshop@example.com";
            helper.setFrom(senderEmail, senderName);
            mailSender.send(message);
            System.out.println("Send email successful");
            return true;
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("Error while send email ", e);
            return false;
        }
    }

    @Override
    public MessageDto sendMailForgotPass(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Email không tồn tại trong hệ thống!");
        }
        String charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()";
        SecureRandom random = new SecureRandom();
        StringBuilder newPass = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(charset.length());
            char randomChar = charset.charAt(index);
            newPass.append(randomChar);
        }
        CompletableFuture.runAsync(() -> {
            String subject = "Mật khẩu mới trên hệ thống BookShop";
            String text = "<strong>Xin chào, <em>" + user.getName() + "</em></strong>" +
                    "<p>Chúng tôi đã nhận được yêu cầu đặt lại mật khẩu BookShop của bạn.</p>" +
                    "<p>Mật khẩu mới của bạn là: <strong>" + newPass + "</strong></p>" +
                    "<p>Trân trọng,</p>" +
                    "<p>Đội ngũ BookShop.</p>";
            sendEmail(email, subject, text);
        }, asyncExecutor);
        user.setPassword(bCryptPasswordEncoder.encode(newPass));
        userRepository.save(user);
        return new MessageDto("Đã gửi mật khẩu mới thông qua email của bạn!");
    }
}
