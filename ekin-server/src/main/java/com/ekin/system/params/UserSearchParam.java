package com.ekin.system.params;

import com.cartisan.repositories.Query;
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
    @Query(propName = "username", type = Query.Type.INNER_LIKE)
    private String username;

    @ApiModelProperty(value = "性别")
    @Query(propName = "sex", type = Query.Type.EQUAL)
    private Integer sex;

    @ApiModelProperty(value = "电话")
    @Length(min = 1, max = 13, message = "电话长度需要在13个字以内")
    @Query(propName = "phone", type = Query.Type.INNER_LIKE)
    private String phone;

    @ApiModelProperty(value = "邮箱")
    @Length(min = 5, max = 50, message = "邮箱长度需要在50个字符以内")
    @Query(type = Query.Type.INNER_LIKE)
    private String email;

    @ApiModelProperty(value = "状态")
    @Query(propName = "status", type = Query.Type.EQUAL)
    private Integer status;
}
