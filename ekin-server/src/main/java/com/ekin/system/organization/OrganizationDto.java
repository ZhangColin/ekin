package com.ekin.system.organization;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author colin
 */
@Data
public class OrganizationDto {
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

    @Setter
    @JsonProperty("children")
    private List<OrganizationDto> childOrganizations;

    public static List<OrganizationDto> buildOrganizationTreeList(List<OrganizationDto> organizations) {
        Multimap<String, OrganizationDto> organizationMap = ArrayListMultimap.create();
        organizations.forEach(organization -> organizationMap.put(organization.getParentId(), organization));

        return buildMenuTreeList("0", organizationMap);
    }

    private static List<OrganizationDto> buildMenuTreeList(String parentId, Multimap<String, OrganizationDto> organizationMap) {
        return organizationMap.get(parentId).stream()
                .peek(organization -> organization.setChildOrganizations(buildMenuTreeList(organization.getId(), organizationMap)))
                .collect(Collectors.toList());
    }
}
