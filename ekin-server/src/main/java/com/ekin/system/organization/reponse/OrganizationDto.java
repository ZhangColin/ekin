//package com.ekin.system.organization.reponse;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.google.common.collect.ArrayListMultimap;
//import com.google.common.collect.Multimap;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//import lombok.Setter;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @author colin
// */
//@Data
//public class OrganizationDto {
//    @ApiModelProperty(value = "组织Id")
//    private Long id;
//
//    @ApiModelProperty(value = "组织名称")
//    private String name;
//
//    @ApiModelProperty(value = "上级组织")
//    private Long parentId;
//
//    @ApiModelProperty(value = "描述")
//    private String description;
//
//    @ApiModelProperty(value = "组织排序")
//    private Integer sort;
//
//    @Setter
//    @JsonProperty("children")
//    private List<OrganizationDto> childOrganizations;
//
//    public static List<OrganizationDto> buildOrganizationTreeList(List<OrganizationDto> organizations) {
//        Multimap<Long, OrganizationDto> organizationMap = ArrayListMultimap.create();
//        organizations.forEach(organization -> organizationMap.put(organization.getParentId(), organization));
//
//        return buildMenuTreeList(0L, organizationMap);
//    }
//
//    private static List<OrganizationDto> buildMenuTreeList(Long parentId, Multimap<Long, OrganizationDto> organizationMap) {
//        return organizationMap.get(parentId).stream()
//                .peek(organization -> {
//                    final List<OrganizationDto> childOrganizations = buildMenuTreeList(organization.getId(), organizationMap);
//                    if (childOrganizations.size()>0) {
//                        organization.setChildOrganizations(childOrganizations);
//                    }
//                })
//                .collect(Collectors.toList());
//    }
//}
