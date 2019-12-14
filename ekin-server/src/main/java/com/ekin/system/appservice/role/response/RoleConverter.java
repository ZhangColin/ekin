package com.ekin.system.appservice.role.response;

import com.ekin.system.domain.role.Role;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

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
