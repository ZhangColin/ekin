package com.ekin.system.dtos;

import com.ekin.system.domains.Role;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author colin
 */
@Mapper(componentModel = "spring")
public interface RoleConverter {

    @Mappings({})
    RoleDto convert(Role role);

    @InheritConfiguration
    List<RoleDto> convert(List<Role> roles);
}
