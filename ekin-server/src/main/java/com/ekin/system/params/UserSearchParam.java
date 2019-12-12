package com.ekin.system.params;

import com.cartisan.repositories.Condition;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author colin
 */
@Data
public class UserSearchParam {
    @ApiModelProperty(value = "账号", required = true)
    @Length(min = 2, max =32, message = "部门名称必须在 2 至 32 之间")
    @Condition(propName = "username", type = Condition.Type.INNER_LIKE)
    private String username;

    @ApiModelProperty(value = "性别")
    @Condition(propName = "sex", type = Condition.Type.EQUAL)
    private Integer sex;

    @ApiModelProperty(value = "电话")
    @Length(min = 1, max = 13, message = "电话长度需要在13个字以内")
    @Condition(propName = "phone", type = Condition.Type.INNER_LIKE)
    private String phone;

    @ApiModelProperty(value = "邮箱")
    @Length(min = 5, max = 50, message = "邮箱长度需要在50个字符以内")
    @Condition(type = Condition.Type.INNER_LIKE)
    private String email;

    @ApiModelProperty(value = "状态")
    @Condition(propName = "status", type = Condition.Type.EQUAL)
    private Integer status;
}
