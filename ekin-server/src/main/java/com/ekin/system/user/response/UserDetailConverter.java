package com.ekin.system.user.response;

import com.cartisan.dtos.Converter;
import com.ekin.system.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author colin
 */
@Mapper
public interface UserDetailConverter extends Converter<User, UserDetailDto>{
    UserDetailConverter CONVERTER = Mappers.getMapper(UserDetailConverter.class);
}
