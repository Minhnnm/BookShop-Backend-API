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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
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

    @GetMapping("/all")
    public ResponseEntity<?> getUserNumber() {
        return ResponseEntity.ok(userService.getAll());
//        return ResponseEntity.ok(new Message("Số lượng khách hàng sử dụng dịch vụ: " + customerService.getAll().size()));
    }
    @PostMapping("")
    public ResponseEntity<?> register(@RequestBody UserRequestDto userRequestDto) {
//        if (customerService.isEmailExists(email)) {
//            Map<String, Object> response = new HashMap<>();
//            Error error = new Error(409, "USR_04", "Email này đã tồn tại trong hệ thống!", "email");
//            response.put("error", error);
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
//        } else {
//            Customer customer = new Customer();
//            WishList wishList = new WishList();
//            Cart cart = new Cart();
//            customer.setEmail(email);
//            customer.setName(name);
//            customer.setPassword(bCryptPasswordEncoder.encode(password));
////            customer.setAvatar("");
//            customer.setRole("user");
//            customer.setStatus("active");
//            String accessToken = jwtUtil.generateToken(customer);
//            customerService.save(customer);
//            wishList.setCustomer(customer);
//            wishListService.save(wishList);
//            cart.setId(new CartUtil().generateCartId());
//            cart.setCustomer(customer);
//            cartService.save(cart);
//            CustomerResponse response = new CustomerResponse("Bearer " + accessToken, customer, "15 days");
//            return ResponseEntity.ok(response);
//        }
        return ResponseEntity.ok(userService.save(userRequestDto));
    }
}
