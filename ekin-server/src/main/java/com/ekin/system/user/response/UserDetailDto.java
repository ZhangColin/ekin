package com.ekin.system.user.response;

import com.ekin.system.user.domain.User;
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
public class UserDetailDto {
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
    private List<String> roleIds;

    public UserDetailDto() {
    }

    public static UserDetailDto convertFrom(User user) {
        return new UserDetailDto(user.getId().toString(), user.getUsername(), user.getRealName(), user.getAvatar(),
                user.getBirthday(), user.getSex(), user.getPhone(), user.getEmail(), user.getStatus(),
                user.getOrganizations().stream().map(userDepartment->userDepartment.getOrganizationId().toString()).collect(toList()),
                user.getRoles().stream().map(userRole -> userRole.getRoleId().toString()).collect(toList()));
    }
}
