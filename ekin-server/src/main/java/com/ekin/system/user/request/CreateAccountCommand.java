package com.ekin.system.user.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import java.util.List;

/**
 * @author colin
 */
@Data
public class CreateAccountCommand {
    @ApiModelProperty(value = "用户名", required = true)
    @Length(min = 2, max = 32, message = "用户名必须在 2 至 32 之间")
    private String username;

    @ApiModelProperty(value = "电话")
    @Length(min = 11, max = 11, message = "电话长度需要在11个字以内")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    @Email
    @Length(min = 5, max = 64, message = "邮箱长度需要在64个字符以内")
    private String email;

    @ApiModelProperty(value = "真实姓名")
    @Length(min = 2, max = 32, message = "真实姓名必须在 2 至 32 之间")
    private String realName;

    @ApiModelProperty(value = "分配的组织")
    private Long organizationId;

    @ApiModelProperty(value = "分配的角色列表")
    private List<Long> roleIds;
}
