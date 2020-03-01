package com.ekin.system.user.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author colin
 */
@Data
public class AssignOrganizationsCommand {
    @ApiModelProperty(value = "分配的组织")
    private Long organizationId;
}
