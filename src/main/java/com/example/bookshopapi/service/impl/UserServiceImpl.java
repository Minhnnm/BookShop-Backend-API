package com.example.bookshopapi.service.impl;

import com.example.bookshopapi.dto.user.UserRequestDto;
import com.example.bookshopapi.entity.Cart;
import com.example.bookshopapi.entity.User;
import com.example.bookshopapi.entity.WishList;
import com.example.bookshopapi.exception.ExistedException;
import com.example.bookshopapi.repository.CartRepository;
import com.example.bookshopapi.repository.RoleRepository;
import com.example.bookshopapi.repository.UserRepository;
import com.example.bookshopapi.repository.WishListRepository;
import com.example.bookshopapi.service.UserService;
import com.example.bookshopapi.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
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

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(UserRequestDto userDto) {
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
//        CustomerResponse response = new CustomerResponse("Bearer " + accessToken, customer, "15 days");

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(userName);
        if (user != null) {
            throw new UsernameNotFoundException("User Not Found");
        }
        return null;
    }
}
