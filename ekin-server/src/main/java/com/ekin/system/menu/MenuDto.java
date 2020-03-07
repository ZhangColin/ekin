package com.ekin.system.menu;

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
public class MenuDto {
    @ApiModelProperty(value = "菜单Id")
    private String id;

    @ApiModelProperty(value = "菜单名称")
    private String title;

    @ApiModelProperty(value = "上级菜单")
    private String parentId;

    @ApiModelProperty(value = "前端名称")
    private String name;

    @ApiModelProperty(value = "前端图标")
    private String icon;

    @ApiModelProperty(value = "是否隐藏")
    private Boolean hidden;

    @ApiModelProperty(value = "菜单排序")
    private Integer sort;

    @Setter
    @JsonProperty("children")
    private List<MenuDto> childMenus;

    public static List<MenuDto> buildMenuTreeList(List<MenuDto> menus) {
        Multimap<String, MenuDto> menuMap = ArrayListMultimap.create();
        menus.forEach(menu -> menuMap.put(menu.getParentId(), menu));

        return buildMenuTreeList("0", menuMap);
    }

    private static List<MenuDto> buildMenuTreeList(String parentId, Multimap<String, MenuDto> menuMap) {
        return menuMap.get(parentId).stream()
                .peek(menu -> {
                    final List<MenuDto> childMenus = buildMenuTreeList(menu.getId(), menuMap);
                    if (childMenus.size()>0) {
                        menu.setChildMenus(childMenus);
                    }
                })
                .collect(Collectors.toList());
    }
}
