package com.ekin.systemcontext.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author colin
 */
@Data
public class RoleParam {
    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank(message = "角色名称不能为空")
    @Length(min = 2, max = 32, message = "角色名称必须在 2 至 32 之间")
    private String name;

    @ApiModelProperty(value = "角色编码", required = true)
    @NotBlank(message = "角色编码不能为空")
    @Length(min = 2, max = 32, message = "角色名称必须在 2 至 32 之间")
    private String code;

    @ApiModelProperty(value = "描述")
    @Length(max = 255, message = "备注长度需要在 255 字以内")
    private String description;
}
