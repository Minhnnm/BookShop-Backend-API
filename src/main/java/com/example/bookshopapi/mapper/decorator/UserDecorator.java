package com.example.bookshopapi.mapper.decorator;

import com.example.bookshopapi.dto.user.UserDto;
import com.example.bookshopapi.entity.User;
import com.example.bookshopapi.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.List;

public abstract class UserDecorator implements UserMapper {
    @Autowired
    @Qualifier("delegate")
    private UserMapper delegate;

    @Override
    public UserDto toDto(User entity) {
        UserDto userDto = delegate.toDto(entity);
        if (entity.getRole() != null) {
            userDto.setRoleUser(entity.getRole().getName());
        }
        return userDto;
    }

    @Override
    public List<UserDto> toDtos(List<User> entities) {
        if (entities == null)
            return null;
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : entities) {
            userDtos.add(toDto(user));
        }
        return userDtos;
    }
}
