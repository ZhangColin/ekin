package com.ekin.system.organization.reponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author colin
 */
@Data
public class OrganizationTreeNode {
    @ApiModelProperty(value = "组织Id")
    private String id;

    @ApiModelProperty(value = "组织名称")
    private String name;

    @ApiModelProperty(value = "上级组织")
    private String parentId;

    @ApiModelProperty(value = "组织排序")
    private Integer sort;

    @Setter
    @JsonProperty("children")
    private List<OrganizationTreeNode> childOrganizations;

    public static List<OrganizationTreeNode> buildOrganizationTreeList(List<OrganizationTreeNode> organizations) {
        Multimap<String, OrganizationTreeNode> organizationMap = ArrayListMultimap.create();
        organizations.forEach(organization -> organizationMap.put(organization.getParentId(), organization));

        return buildMenuTreeList("0", organizationMap);
    }

    private static List<OrganizationTreeNode> buildMenuTreeList(String parentId, Multimap<String, OrganizationTreeNode> organizationMap) {
        return organizationMap.get(parentId).stream()
                .peek(organization -> organization.setChildOrganizations(buildMenuTreeList(organization.getId(), organizationMap)))
                .collect(Collectors.toList());
    }
}
