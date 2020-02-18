package com.ekin.system.user.response;

import com.ekin.system.user.domain.User;
import com.ekin.system.user.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

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
