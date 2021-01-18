package com.ekin.system.user.request;

import com.ekin.system.user.domain.Gender;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import java.util.List;

/**
 * @author colin
 */
@Data
public class CreateAccountCommand {
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    @Email
    private String email;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "分配的组织")
    private List<Long> organizationIds;

    @ApiModelProperty(value = "分配的角色列表")
    private List<Long> roleIds;

    @ApiModelProperty(value = "性别")
    private Gender gender;
}
