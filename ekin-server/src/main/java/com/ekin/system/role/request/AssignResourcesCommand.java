package com.ekin.system.role.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author colin
 */
@Data
public class AssignResourcesCommand {
    @ApiModelProperty(value = "分配的资源")
    private List<Long> resourceIds;
}
