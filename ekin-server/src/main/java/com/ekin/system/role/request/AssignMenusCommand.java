package com.ekin.system.role.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author colin
 */
@Data
public class AssignMenusCommand {
    @ApiModelProperty(value = "分配的菜单")
    private List<Long> menuIds;
}
