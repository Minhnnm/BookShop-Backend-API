package com.example.bookshopapi.mapper;

import com.example.bookshopapi.dto.user.UserDto;
import com.example.bookshopapi.entity.User;
import com.example.bookshopapi.mapper.decorator.UserDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
@DecoratedWith(UserDecorator.class)
public interface UserMapper extends EntityMapper<User, UserDto> {
}
