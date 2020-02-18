package com.ekin.system.user.response;

import com.cartisan.dtos.Converter;
import com.ekin.system.user.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserConverter extends Converter<User, UserDto> {
}
