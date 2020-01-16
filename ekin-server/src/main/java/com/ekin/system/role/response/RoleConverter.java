package com.ekin.system.role.response;

import com.cartisan.dtos.Converter;
import com.ekin.system.role.domain.Role;
import org.mapstruct.Mapper;

/**
 * @author colin
 */
@Mapper(componentModel = "spring")
public interface RoleConverter extends Converter<Role, RoleDto> {
}