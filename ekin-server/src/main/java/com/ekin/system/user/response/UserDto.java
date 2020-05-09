package com.ekin.system.user.response;

import com.ekin.system.user.domain.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author colin
 */
@Data
public class UserDto {
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
    private Gender gender;

    @ApiModelProperty(value = "状态")
    private Integer status;
}
