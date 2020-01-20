package com.ekin.system.user.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author colin
 */
@Data
public class AssignRolesCommand {
    @ApiModelProperty(value = "分配的角色编码")
    private List<String> roleCodes;
}
