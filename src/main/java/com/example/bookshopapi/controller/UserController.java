package com.example.bookshopapi.controller;

import com.example.bookshopapi.config.jwt.JwtUtil;
import com.example.bookshopapi.dto.user.UserRequestDto;
import com.example.bookshopapi.entity.Cart;
import com.example.bookshopapi.entity.User;
import com.example.bookshopapi.entity.WishList;
import com.example.bookshopapi.service.CartService;
import com.example.bookshopapi.service.EmailService;
import com.example.bookshopapi.service.UserService;
import com.example.bookshopapi.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private WishListService wishListService;
    @Autowired
    private CartService cartService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/test")
    public ResponseEntity<?> getUserNumber() {
        return ResponseEntity.ok(userService.getAll());
//        return ResponseEntity.ok(new Message("Số lượng khách hàng sử dụng dịch vụ: " + customerService.getAll().size()));
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(userService.register(userRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDto userRequestDto){
        return ResponseEntity.ok(userService.login(userRequestDto));
    }
}
