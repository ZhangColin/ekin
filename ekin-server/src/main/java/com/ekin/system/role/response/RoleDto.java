package com.ekin.system.role.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author colin
 */
@Data
public class RoleDto {
    @ApiModelProperty(value = "角色Id")
    private String id;

    @ApiModelProperty(value = "角色名称")
    private String name;
}
