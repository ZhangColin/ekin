package com.ekin.system.organization.reponse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author colin
 */
@Data
public class OrganizationDto {
    @ApiModelProperty(value = "组织Id")
    private String id;

    @ApiModelProperty(value = "组织名称")
    private String name;
}
