package com.example.bookshopapi.service.impl;

import com.example.bookshopapi.dto.MessageDto;
import com.example.bookshopapi.entity.CartItem;
import com.example.bookshopapi.entity.Order;
import com.example.bookshopapi.entity.Receiver;
import com.example.bookshopapi.entity.User;
import com.example.bookshopapi.repository.UserRepository;
import com.example.bookshopapi.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.List;
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
//        String charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()";
//        SecureRandom random = new SecureRandom();
//        StringBuilder newPass = new StringBuilder();
//        for (int i = 0; i < 8; i++) {
//            int index = random.nextInt(charset.length());
//            char randomChar = charset.charAt(index);
//            newPass.append(randomChar);
//        }
        String newPass = RandomStringUtils.random(8, true, true);
        user.setPassword(bCryptPasswordEncoder.encode(newPass));
        userRepository.save(user);
        CompletableFuture.runAsync(() -> {
            String subject = "Mật khẩu mới trên hệ thống BookShop";
            String text = "<strong>Xin chào, <em>" + user.getName() + "</em></strong>" +
                    "<p>Chúng tôi đã nhận được yêu cầu đặt lại mật khẩu BookShop của bạn.</p>" +
                    "<p>Mật khẩu mới của bạn là: <strong>" + newPass + "</strong></p>" +
                    "<p>Trân trọng,</p>" +
                    "<p>Đội ngũ BookShop.</p>";
            sendEmail(email, subject, text);
        }, asyncExecutor).exceptionally(ex -> {
            log.error("Failed to send email: " + ex.getMessage());
            return null;
        });
        return new MessageDto("Đã gửi mật khẩu mới thông qua email của bạn!");
    }

    @Override
    public void sendMailOrder(Receiver receiver, User user, Order order, List<CartItem> cartItems){
        String subject = "Đơn hàng của bạn đã được đặt thành công!";
        StringBuilder text = new StringBuilder();
        text.append("<strong>Xin chào: <em>").append(user.getName()).append(",</em> cảm ơn bạn vì đã đặt hàng. </strong>")
                .append("<p>Thời gian đặt hàng: ").append(order.getCreateOn()).append("</p>").append("<p>Tên người nhận hàng: <em>")
                .append(receiver.getReceiverName()).append("</em></p>").append("<p>Số điện thoại người nhận: <em>")
                .append(receiver.getReceiverPhone()).append("</em></p>").append("<p>Địa chỉ người nhận: <em>")
                .append(receiver.getAddress()).append("</em></p>")
                .append("<p style='font-weight: bold;'>SAU ĐÂY LÀ THÔNG TIN CHI TIẾT ĐƠN HÀNG CỦA BẠN:</p>")
                .append("<table>").append("<tr>")
                .append("<th>STT</th>").append("<th>Ảnh sản phẩm</th>").append("<th>Tên sản phẩm</th>").append("<th>Đơn giá</th>").append("<th>Số lượng</th>").append("</tr>");
        int index = 0;
        BigDecimal subTotal = new BigDecimal("0");
        DecimalFormat formatter = new DecimalFormat("#,### đ");
        for (CartItem cartItem : cartItems) {
            index++;
            subTotal = subTotal.add(cartItem.getProduct().getDiscountedPrice().multiply(new BigDecimal(cartItem.getQuantity())));
            text.append("<tr>" + "<td width='50' height='175' style='text-align: center;'>")
                    .append(index).append("</td>")
                    .append("<td width='200' height='175' style='text-align: center;'><img src='")
                    .append(cartItem.getProduct().getImage()).append("' alt='")
                    .append(cartItem.getProduct().getName()).append("' width='140' height='140'></td>")
                    .append("<td width='500' height='175' style='text-align: center;'>")
                    .append(cartItem.getProduct().getName()).append("</td>")
                    .append("<td width='125' height='175' style='text-align: center;'>")
                    .append(formatter.format(cartItem.getProduct().getDiscountedPrice()))
                    .append("</td>").append("<td width='100' height='175' style='text-align: center;'>")
                    .append(cartItem.getQuantity()).append("</td>").append("</tr>");
        }
        text.append("</table>" + "<span style='font-weight: bold;'>Tổng tiền: ")
                .append(formatter.format(subTotal))
                .append("</span><br>")
                .append("<span style='font-weight: bold;'>Phí vận chuyển: ")
                .append(formatter.format(order.getShipping().getShippingCost()))
                .append("</span><br>").append("<span style='font-weight: bold;'>Tổng thanh toán: ")
                .append(formatter.format(subTotal.add(order.getShipping().getShippingCost())))
                .append("</span></br>").append("<hr>").append("<span>Trân trọng,</span><br>")
                .append("<span>Đội ngũ BookShop.</span>");
        String email=user.getEmail();
        sendEmail(email, subject, text.toString());
    }
}
