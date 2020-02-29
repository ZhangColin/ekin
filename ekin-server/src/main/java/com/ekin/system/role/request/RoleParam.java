package com.ekin.system.role.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author colin
 */
@Data
public class RoleParam {
    @ApiModelProperty(value = "角色名称", required = true)
    @NotBlank(message = "角色名称不能为空")
    @Length(min = 2, max = 32, message = "角色名称必须在 2 至 32 之间")
    private String name;

    @ApiModelProperty(value = "角色排序")
    @NotNull(message = "角色排序不能为空")
    @Min(value = 0, message = "排序最小为0")
    private Integer sort;

    @ApiModelProperty(value = "描述")
    @Length(max = 255, message = "备注长度需要在 255 字以内")
    private String description;
}
