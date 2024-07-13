package com.example.bookshopapi.controller;

import com.example.bookshopapi.config.jwt.JwtUtil;
import com.example.bookshopapi.dto.user.UserDto;
import com.example.bookshopapi.dto.user.UserRequestDto;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/test")
    public ResponseEntity<?> getUserNumber() {
        return ResponseEntity.ok(userService.getAll());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(userService.register(userRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.ok(userService.login(userRequestDto));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPass(@RequestParam("email") String email) {
        return ResponseEntity.ok(emailService.sendMailForgotPass(email));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("change-password")
    public ResponseEntity<?> changePassword(@RequestParam("old_password") String oldPassword,
                                            @RequestParam("new_password") String newPassword) {
        return ResponseEntity.ok(userService.changePassword(oldPassword, newPassword));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/change-avatar")
    public ResponseEntity<?> changeAvatar(@RequestPart("image") MultipartFile multipartFile) {
        return ResponseEntity.ok(userService.changeAvatar(multipartFile));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping(path = "/profile")
    public ResponseEntity<?> getProfile() {
        return ResponseEntity.ok(userService.getProfile());
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllUser(@RequestParam(name = "query", defaultValue = "") String query,
                                        @RequestParam(name = "type_account", required = false) String typeAccount,
                                        @RequestParam(name = "status", required = false) Integer status,
                                        @RequestParam(name = "limit", defaultValue = "10") int limit,
                                        @RequestParam(name = "page", defaultValue = "1") int page,
                                        @RequestParam(name = "sort_by", defaultValue = "createdDate") String sortBy,
                                        @RequestParam(name = "sort_dir", defaultValue = "desc") String sortDir) {
        return ResponseEntity.ok(userService.findAll(query, status, typeAccount, sortBy, sortDir, page, limit));
    }

//    @PreAuthorize("hasAnyRole('ADMIN')")
//    @DeleteMapping("/delete")
//    public ResponseEntity<?> deleteUserById(@RequestParam("id") UUID userId) {
//        return ResponseEntity.ok(userService.deleteUser(userId));
//    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/update-status")
    public ResponseEntity<?> updateStatus(@RequestParam("user_id") UUID id,
                                          @RequestParam("status") int status) {
        return ResponseEntity.ok(userService.updateStatus(id, status));
    }
}
