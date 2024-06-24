package com.example.bookshopapi.service.impl;

import com.example.bookshopapi.config.jwt.JwtUtil;
import com.example.bookshopapi.dto.user.LoginResponse;
import com.example.bookshopapi.dto.user.UserDto;
import com.example.bookshopapi.dto.user.UserRequestDto;
import com.example.bookshopapi.entity.Cart;
import com.example.bookshopapi.entity.User;
import com.example.bookshopapi.entity.WishList;
import com.example.bookshopapi.exception.BadRequestException;
import com.example.bookshopapi.exception.ExistedException;
import com.example.bookshopapi.mapper.UserMapper;
import com.example.bookshopapi.repository.CartRepository;
import com.example.bookshopapi.repository.RoleRepository;
import com.example.bookshopapi.repository.UserRepository;
import com.example.bookshopapi.repository.WishListRepository;
import com.example.bookshopapi.service.UserService;
import com.example.bookshopapi.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService, UserDetails {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDto save(UserRequestDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new ExistedException("USR_01", "Email đã tồn tại", "email");
        }
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        WishList wishList = new WishList();
        Cart cart = new Cart();
        user.setAvatar("");
        user.setRole(roleRepository.getById(2));
//        user.setStatus("active");
//        String accessToken = jwtUtil.generateToken(customer);
        userRepository.save(user);
        wishList.setUser(user);
        wishListRepository.save(wishList);
        cart.setUser(user);
        cartRepository.save(cart);
        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public LoginResponse login(UserRequestDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail());
        if (user == null) {
            throw new UsernameNotFoundException("Email không tồn tài trong hệ thống!");
        }
        if (user.getStatus() != 1) {
            throw new BadRequestException("USR_03", "Tài khoản đã bị khóa", "");
        }
        if (!bCryptPasswordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new BadRequestException("USR_02", "Sai tên tài khoản hoặc mật khẩu", "");
        }
        String accessToken = jwtUtil.generateToken(user);
        LocalDateTime expiredTime = jwtUtil.extractExpiration(accessToken);
        long expiresIn = ChronoUnit.HOURS.between(LocalDateTime.now().with(ChronoField.MILLI_OF_SECOND, 0), expiredTime);
        LoginResponse loginResponse = LoginResponse.builder()
                .accessToken("Bearer " + accessToken)
                .userDto(userMapper.toDto(user))
                .expires_in(expiresIn + "hours").build();
        return loginResponse;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
