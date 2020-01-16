package com.ekin.system.permission;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author colin
 */
@Getter
@AllArgsConstructor
public class PermissionDto {
    private String id;

    private String name;
    private String parentId;
    private String code;
    private String description;
    private Integer sort;

    @Setter
    @JsonProperty("children")
    private List<PermissionDto> childPermissions;

    public static List<PermissionDto> buildPermissionTreeList(List<Permission> permissions) {
        Multimap<Long, Permission> permissionMap = ArrayListMultimap.create();
        permissions.forEach(permission -> permissionMap.put(permission.getParentId(), permission));

        return buildPermissionTreeList(0L, permissionMap);
    }

    private static List<PermissionDto> buildPermissionTreeList(Long parentId, Multimap<Long, Permission> permissionMap) {
        return permissionMap.get(parentId).stream().map(permission -> new PermissionDto(
                permission.getId().toString(), permission.getName(), permission.getParentId().toString(),
                permission.getCode(), permission.getDescription(), permission.getSort(),
                buildPermissionTreeList(permission.getId(), permissionMap))).collect(Collectors.toList());
    }
}
