package com.ekin.system.organization.reponse;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author colin
 */
@Data
public class OrganizationDetailDto {
    @ApiModelProperty(value = "组织Id")
    private String id;

    @ApiModelProperty(value = "组织名称")
    private String name;

    @ApiModelProperty(value = "上级组织")
    private String parentId;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "组织排序")
    private Integer sort;
}
