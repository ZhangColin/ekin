package com.ekin.system.menu;

import com.cartisan.dp.OnOffStatus;
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
public class MenuParam {
    @ApiModelProperty(value = "上级菜单规则")
    private Long parentId;

    @ApiModelProperty(value = "菜单规则类型")
    @NotBlank(message = "菜单规则类型不能为空")
    private String type;

    @ApiModelProperty(value = "标题", required = true)
    @NotBlank(message = "标题不能为空")
    @Length(min = 2, max = 64, message = "标题必须在 2 至 64 之间")
    private String title;

    @ApiModelProperty(value = "规则名称")
    @NotBlank(message = "规则名称不能为空")
    private String name;

    @ApiModelProperty(value = "路由路径")
    private String path;

    @ApiModelProperty(value = "组件路径")
    private String component;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "菜单类型")
    private String menuType;

    @ApiModelProperty(value = "Url")
    private String url;

    @ApiModelProperty(value = "缓存")
    private OnOffStatus keepalive;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "菜单排序")
    @Min(value = 0, message = "排序最小为0")
    private Integer sequence;

    @ApiModelProperty(value = "状态")
    @NotNull(message = "状态不能为空")
    private OnOffStatus status;
}
