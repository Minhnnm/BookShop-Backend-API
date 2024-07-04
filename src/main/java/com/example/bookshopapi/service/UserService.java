package com.example.bookshopapi.service;

import com.example.bookshopapi.dto.MessageDto;
import com.example.bookshopapi.dto.user.LoginResponse;
import com.example.bookshopapi.dto.user.UserDto;
import com.example.bookshopapi.dto.user.UserRequestDto;
import com.example.bookshopapi.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    List<User> getAll();

    UserDto register(UserRequestDto userDto);

    LoginResponse login(UserRequestDto userDto);

    MessageDto changePassword(String oldPassword, String newPassword);

    MessageDto changeAvatar(MultipartFile multiPartFile);

    UserDto getProfile();

    MessageDto updateUser(UserDto userDto);

    List<UserDto> findAll(String query, int status, String typeAccount, String sortBy, String sortDir, int page, int limit);

    MessageDto deleteUser(int userId);

    MessageDto updateStatus(int userId, int status);
}
