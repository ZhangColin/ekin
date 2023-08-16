package com.ekin.system.role.response;

import com.cartisan.dp.OnOffStatus;
import com.ekin.system.menu.MenuDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author colin
 */
@Data
public class RoleDto {
    @ApiModelProperty(value = "角色Id")
    private String id;

    @ApiModelProperty(value = "上级菜色")
    private String parentId;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "权限规则")
    private List<String> rules = new ArrayList<>();

    @ApiModelProperty(value = "状态")
    private OnOffStatus status;

    @Setter
    @JsonProperty("children")
    private List<RoleDto> childRoles;

    public static List<RoleDto> buildRoleTreeList(List<RoleDto> roles) {
        Multimap<String, RoleDto> roleMap = ArrayListMultimap.create();
        roles.forEach(role -> roleMap.put(role.getParentId(), role));

        return buildRoleTreeList("0", roleMap);
    }

    private static List<RoleDto> buildRoleTreeList(String parentId, Multimap<String, RoleDto> roleMap) {
        return roleMap.get(parentId).stream()
                .peek(role -> {
                    final List<RoleDto> childMenus = buildRoleTreeList(role.getId(), roleMap);
                    if (childMenus.size()>0) {
                        role.setChildRoles(childMenus);
                    }
                })
                .collect(Collectors.toList());
    }
}
