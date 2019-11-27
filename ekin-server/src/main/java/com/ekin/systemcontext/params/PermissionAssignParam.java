package com.ekin.systemcontext.params;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author colin
 */
@Data
public class PermissionAssignParam {
    @ApiModelProperty(value = "角色")
    @NotNull(message = "必须指定角色")
    private Long roleId;

    @ApiModelProperty(value = "分配的权限列表")
    private List<Long> permissionIds;

}
