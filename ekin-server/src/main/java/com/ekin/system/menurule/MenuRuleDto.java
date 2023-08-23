package com.ekin.system.menurule;

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
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author colin
 */
@Data
public class MenuRuleDto {
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
    private List<MenuRuleDto> childMenuRules;

    public static List<MenuRuleDto> buildMenuRuleTreeList(List<MenuRuleDto> menuRuleDtos) {
        Multimap<String, MenuRuleDto> menuRuleDtoMultimap = getMenuRuleMap(menuRuleDtos);

        List<String> parentMenuRuleIds = getParentMenuRuleIds(menuRuleDtos);

        return parentMenuRuleIds.stream().map(topMenuRuleId ->
                buildMenuRuleTreeList(topMenuRuleId, menuRuleDtoMultimap)).flatMap(List::stream).collect(toList());
    }

    private static List<MenuRuleDto> buildMenuRuleTreeList(String parentId, Multimap<String, MenuRuleDto> menuRuleMap) {
        return menuRuleMap.get(parentId).stream()
                .peek(menuRuleDto -> {
                    final List<MenuRuleDto> childMenuRules = buildMenuRuleTreeList(menuRuleDto.getId(), menuRuleMap);
                    if (!childMenuRules.isEmpty()) {
                        menuRuleDto.setChildMenuRules(childMenuRules);
                    }
                })
                .collect(toList());
    }


    public static List<MenuRuleDto> buildMenuRuleOptions(List<MenuRuleDto> menuRuleDtos) {
        Multimap<String, MenuRuleDto> menuRuleDtoMultimap = getMenuRuleMap(menuRuleDtos);

        List<String> parentMenuRuleIds = getParentMenuRuleIds(menuRuleDtos);

        return parentMenuRuleIds.stream().map(topMenuRuleId ->
                buildMenuRuleOptions(topMenuRuleId, 0, menuRuleDtoMultimap)).flatMap(List::stream).collect(toList());
    }

    private static List<MenuRuleDto> buildMenuRuleOptions(String parentId, int level, Multimap<String, MenuRuleDto> menuRuleMap) {
        List<MenuRuleDto> results = new ArrayList<>();

        Collection<MenuRuleDto> menus = menuRuleMap.get(parentId);

        StringBuilder tab = new StringBuilder();
        for (int i = 0; i < level * 2; i++) {
            tab.append(" ");
        }

        int index = 0;
        for (MenuRuleDto menu : menus) {
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

            results.addAll(buildMenuRuleOptions(menu.getId(), level+1, menuRuleMap));
        }

        return results;
    }

    @NotNull
    private static List<String> getParentMenuRuleIds(List<MenuRuleDto> menuRuleDtos) {
        List<String> menuRuleIds = menuRuleDtos.stream().map(MenuRuleDto::getId).collect(toList());
        List<String> parentMenuRuleIds = menuRuleDtos.stream().map(MenuRuleDto::getParentId).distinct().collect(toList());

        parentMenuRuleIds.removeAll(menuRuleIds);
        return parentMenuRuleIds;
    }

    @NotNull
    private static Multimap<String, MenuRuleDto> getMenuRuleMap(List<MenuRuleDto> menuRuleDtos) {
        Multimap<String, MenuRuleDto> menuRuleMap = ArrayListMultimap.create();
        menuRuleDtos.forEach(menuRule -> menuRuleMap.put(menuRule.getParentId(), menuRule));
        return menuRuleMap;
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
