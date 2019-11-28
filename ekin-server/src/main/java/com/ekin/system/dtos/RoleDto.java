package com.ekin.system.dtos;

import com.ekin.system.domains.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author colin
 */
@Getter
@AllArgsConstructor
public class RoleDto {
    private String id;

    private String name;
    private String code;
    private String description;

    public static RoleDto convertFrom(Role role) {
        return new RoleDto(role.getId().toString(), role.getName(),
                role.getCode(), role.getDescription());
    }
}
