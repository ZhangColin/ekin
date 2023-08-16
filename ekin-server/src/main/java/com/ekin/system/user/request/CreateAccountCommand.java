package com.ekin.system.user.request;

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

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "分配的组织")
    private List<Long> organizationIds;

    @ApiModelProperty(value = "分配的角色列表")
    private List<Long> roleIds;
}
