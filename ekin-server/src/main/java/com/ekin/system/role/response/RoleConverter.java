package com.ekin.system.role.response;

import com.cartisan.dto.Converter;
import com.ekin.system.role.domain.Role;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author colin
 */
@Mapper
public interface RoleConverter extends Converter<Role, RoleDto> {
    RoleConverter CONVERTER = Mappers.getMapper(RoleConverter.class);
}