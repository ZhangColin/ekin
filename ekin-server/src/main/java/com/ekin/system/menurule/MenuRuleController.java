package com.ekin.system.menurule;

import com.cartisan.response.GenericResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cartisan.response.ResponseUtil.success;

/**
 * @author colin
 */
@Api(tags = "系统管理：菜单规则")
@RestController
@RequestMapping("/system/menuRules")
@Validated
@Slf4j
public class MenuRuleController {
    private final MenuRuleAppService service;

    public MenuRuleController(MenuRuleAppService service) {
        this.service = service;
    }

    @ApiOperation(value = "获取所有菜单规则")
    @GetMapping("/list")
    public GenericResponse<List<MenuRuleDto>> getMenuTreeList() {
        return success(service.getMenuRuleTreeList());
    }

    @ApiOperation(value = "获取所有菜单规则")
    @GetMapping("/options")
    public GenericResponse<Map<String, Object>> getMenuOptions() {
        List<MenuRuleDto> menuTreeList = service.getMenuRuleOptions();
        Map<String, Object> r = new HashMap<>();
        r.put("options", menuTreeList);

        return success(r);
    }

    @ApiOperation(value = "获取菜单规则")
    @GetMapping("/{id}")
    public GenericResponse<MenuRuleDto> getMenu(
            @ApiParam(value = "菜单规则Id", required = true) @PathVariable Long id) {
        return success(service.getMenuRule(id));
    }

    @ApiOperation(value = "添加菜单规则")
    @PostMapping
    public GenericResponse<MenuRuleDto> addMenu(
            @ApiParam(value = "菜单规则信息", required = true) @Validated @RequestBody MenuRuleParam menuRuleParam) {
        return success(service.addMenuRule(menuRuleParam));
    }

    @ApiOperation(value = "编辑菜单规则")
    @PutMapping("/{id}")
    public GenericResponse<MenuRuleDto> editMenu(
            @ApiParam(value = "菜单规则Id", required = true) @PathVariable Long id,
            @ApiParam(value = "菜单规则信息", required = true) @Validated @RequestBody MenuRuleParam menuRuleParam) {
        return success(service.editMenuRule(id, menuRuleParam));
    }

    @ApiOperation(value = "删除菜单规则")
    @DeleteMapping("/{id}")
    public GenericResponse<?> removeMenu(
            @ApiParam(value = "菜单规则Id", required = true) @PathVariable long id) {
        service.removeMenuRule(id);
        return success();
    }
}
