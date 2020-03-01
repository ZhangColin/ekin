package com.ekin.system.user.response;

import com.ekin.system.organization.OrganizationDto;
import com.ekin.system.role.response.RoleDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * @author colin
 */
@Data
public class UserDetailDto {
    @ApiModelProperty(value = "用户Id")
    private String id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "状态")
    private Integer status;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "生日")
    private LocalDate birthday;

    @ApiModelProperty(value = "拥有的角色")
    private List<RoleDto> roles;

    @ApiModelProperty(value = "所属组织")
    private OrganizationDto organization;

}
