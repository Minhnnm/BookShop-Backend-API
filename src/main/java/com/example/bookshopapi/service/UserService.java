package com.example.bookshopapi.service;

import com.example.bookshopapi.dto.user.UserDto;
import com.example.bookshopapi.dto.user.UserRequestDto;
import com.example.bookshopapi.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    List<User> getAll();
    User save(UserRequestDto userDto);
}
