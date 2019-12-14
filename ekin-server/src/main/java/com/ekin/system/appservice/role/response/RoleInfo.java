package com.ekin.system.appservice.role.response;

import com.ekin.system.domain.role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author colin
 */
@Data
@AllArgsConstructor
public class RoleInfo {
    private String code;

    private String name;

    public static RoleInfo convertFrom(Role role) {
        return new RoleInfo(role.getCode(), role.getName());
    }
}
