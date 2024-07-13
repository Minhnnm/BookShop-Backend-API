package com.example.bookshopapi.service;

import com.example.bookshopapi.dto.MessageDto;
import com.example.bookshopapi.dto.user.LoginResponse;
import com.example.bookshopapi.dto.user.UserDto;
import com.example.bookshopapi.dto.user.UserRequestDto;
import com.example.bookshopapi.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> getAll();

    UserDto register(UserRequestDto userDto);

    LoginResponse login(UserRequestDto userDto);

    MessageDto changePassword(String oldPassword, String newPassword);

    MessageDto changeAvatar(MultipartFile multiPartFile);

    UserDto getProfile();

    MessageDto updateUser(UserDto userDto);

    List<UserDto> findAll(String query, Integer status, String typeAccount, String sortBy, String sortDir, int page, int limit);

//    MessageDto deleteUser(UUID userId);

    MessageDto updateStatus(UUID id, int status);
}
