package com.ekin.system.role.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author colin
 */
@Data
public class RoleDetailDto {
    @ApiModelProperty(value = "角色Id")
    private String id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色排序")
    private Integer sort;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "菜单Ids")
    private List<String> menuIds;

    @ApiModelProperty(value = "资源Ids")
    private List<String> resourceIds;
}
