package com.ekin.system.menu;

import com.cartisan.dp.OnOffStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author colin
 */
@Data
public class MenuDto {
    @ApiModelProperty(value = "菜单Id")
    private String id;

    @ApiModelProperty(value = "上级菜单规则")
    @JsonSerialize(using = ParentIdSerializer.class)
    private String parentId;

    @ApiModelProperty(value = "菜单规则类型")
    private String type;

    @ApiModelProperty(value = "标题", required = true)
    private String title;

    @ApiModelProperty(value = "规则名称")
    private String name;

    @ApiModelProperty(value = "路由路径")
    private String path;

    @ApiModelProperty(value = "组件路径")
    private String component;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "菜单类型")
    private String menuType;

    @ApiModelProperty(value = "Url")
    private String url;

    @ApiModelProperty(value = "缓存")
    private OnOffStatus keepalive;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "菜单排序")
    private Integer sequence;

    @ApiModelProperty(value = "状态")
    private OnOffStatus status;

    @Setter
    @JsonProperty("children")
    private List<MenuDto> childMenus;

    public static List<MenuDto> buildMenuTreeList(List<MenuDto> menus) {
        Multimap<String, MenuDto> menuMap = ArrayListMultimap.create();
        menus.forEach(menu -> menuMap.put(menu.getParentId(), menu));

        List<String> menuIds = menus.stream().map(MenuDto::getId).collect(toList());
        List<String> parentMenuIds = menus.stream().map(MenuDto::getParentId).distinct().collect(toList());

        parentMenuIds.removeAll(menuIds);

        return parentMenuIds.stream().map(topMenuId -> buildMenuTreeList(topMenuId, menuMap)).flatMap(List::stream).collect(toList());
    }

    private static List<MenuDto> buildMenuTreeList(String parentId, Multimap<String, MenuDto> menuMap) {
        return menuMap.get(parentId).stream()
                .peek(menu -> {
                    final List<MenuDto> childMenus = buildMenuTreeList(menu.getId(), menuMap);
                    if (childMenus.size() > 0) {
                        menu.setChildMenus(childMenus);
                    }
                })
                .collect(toList());
    }


    public static List<MenuDto> buildMenuOptions(List<MenuDto> menus) {
        Multimap<String, MenuDto> menuMap = ArrayListMultimap.create();
        menus.forEach(menu -> menuMap.put(menu.getParentId(), menu));

        List<String> menuIds = menus.stream().map(MenuDto::getId).collect(toList());
        List<String> parentMenuIds = menus.stream().map(MenuDto::getParentId).distinct().collect(toList());

        parentMenuIds.removeAll(menuIds);

        return parentMenuIds.stream().map(topMenuId -> buildMenuOptions(topMenuId, 0, menuMap)).flatMap(List::stream).collect(toList());
    }

    private static List<MenuDto> buildMenuOptions(String parentId, int level, Multimap<String, MenuDto> menuMap) {
        List<MenuDto> results = new ArrayList<>();

        Collection<MenuDto> menus = menuMap.get(parentId);

        StringBuilder tab = new StringBuilder();
        for (int i = 0; i < level * 2; i++) {
            tab.append(" ");
        }

        int index = 0;
        for (MenuDto menu : menus) {
            index++;
            if (level > 0) {
                if (index < menus.size()) {
                    menu.setTitle("├" + menu.getTitle());
                } else {
                    menu.setTitle("└" + menu.getTitle());
                }
            }
            menu.setTitle(tab + menu.getTitle());

            results.add(menu);

            results.addAll(buildMenuOptions(menu.getId(), level+1, menuMap));
        }

        return results;
    }

    protected static class ParentIdSerializer extends com.fasterxml.jackson.databind.JsonSerializer<String>{
        @Override
        public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            if (s.equals("0")){
                jsonGenerator.writeString("");
            }
            else {
                jsonGenerator.writeString(s);
            }
        }
    }
}
