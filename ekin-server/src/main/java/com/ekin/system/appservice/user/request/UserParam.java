package com.ekin.system.appservice.user.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * @author colin
 */
@Data
public class UserParam {
    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    @Length(min = 2, max = 32, message = "部门名称必须在 2 至 32 之间")
    private String username;

    @ApiModelProperty(value = "用户名字", required = true)
    @NotBlank(message = "用户名字不能为空")
    @Length(min = 2, max = 32, message = "部门名称必须在 2 至 32 之间")
    private String realName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "电话")
    @NotBlank(message = "电话不可以为空")
    @Length(min = 1, max = 13, message = "电话长度需要在13个字以内")
    private String phone;

    @ApiModelProperty(value = "邮箱")
    @NotBlank(message = "邮箱不允许为空")
    @Length(min = 5, max = 50, message = "邮箱长度需要在50个字符以内")
    private String email;

    @ApiModelProperty(value = "分配的部门列表")
    private List<Long> departmentIds;

    @ApiModelProperty(value = "分配的角色列表")
    private List<String>  roleCodes;
}
