package com.ekin.system.permission;

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
public class PermissionParam {
    @ApiModelProperty(value = "权限名称", required = true)
    @NotBlank(message = "权限名称不能为空")
    @Length(min = 2, max = 32, message = "权限名称必须在 2 至 32 之间")
    private String name;

    @ApiModelProperty(value = "上级权限")
    private Long parentId;

    @ApiModelProperty(value = "权限编码", required = true)
    @NotBlank(message = "权限编码不能为空")
    @Length(min = 2, max = 255, message = "权限编码必须在 2 至 255 之间")
    private String code;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "排序字段")
    @NotNull(message = "展示顺序不能为空")
    @Min(value = 0, message = "排序最小为0")
    private Integer sort;
}
