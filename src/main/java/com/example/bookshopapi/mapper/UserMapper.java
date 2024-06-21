package com.example.bookshopapi.mapper;

import com.example.bookshopapi.dto.user.UserDto;
import com.example.bookshopapi.entity.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<User, UserDto> {
}
