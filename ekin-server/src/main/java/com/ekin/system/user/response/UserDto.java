package com.ekin.system.user.response;

import com.ekin.system.user.domain.User;
import com.ekin.system.user.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author colin
 */
@Getter
@AllArgsConstructor
public class UserDto {
    private String id;

    private String username;
    private String realName;
    private String avatar;
    private Date birthday;
    private Integer sex;
    private String phone;
    private String email;
    private Integer status;
    private List<String> departmentIds;
    private List<String> roleCodes;

    public UserDto() {
    }

    public static UserDto convertFrom(User user) {
        return new UserDto(user.getId().toString(), user.getUsername(), user.getRealName(), user.getAvatar(),
                user.getBirthday(), user.getSex(), user.getPhone(), user.getEmail(), user.getStatus(),
                user.getDepartments().stream().map(userDepartment->userDepartment.getDepartmentId().toString()).collect(toList()),
                user.getRoles().stream().map(UserRole::getRoleCode).collect(toList()));
    }
}
