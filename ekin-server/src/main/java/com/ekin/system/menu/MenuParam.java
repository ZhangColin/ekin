package com.ekin.system.menu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author colin
 */
@Data
public class MenuParam {
    @ApiModelProperty(value = "菜单名称", required = true)
    @NotBlank(message = "菜单名称不能为空")
    @Length(min = 2, max = 32, message = "权限名称必须在 2 至 32 之间")
    private String title;

    @ApiModelProperty(value = "上级菜单")
    private Long parentId;

    @ApiModelProperty(value = "前端名称")
    @NotBlank(message = "前端名称不能为空")
    private String name;

    @ApiModelProperty(value = "前端图标")
    private String icon;

    @ApiModelProperty(value = "是否隐藏")
    private Boolean hidden;

    @ApiModelProperty(value = "菜单排序")
    @Min(value = 0, message = "排序最小为0")
    private Integer sort;
}
