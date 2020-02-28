package com.ekin.system.user.response;

import lombok.Data;

/**
 * @author colin
 */
@Data
public class UserDto {
    private String id;

    private String username;
    private String realName;
    private Integer sex;
    private String phone;
    private String email;
    private Integer status;
}
