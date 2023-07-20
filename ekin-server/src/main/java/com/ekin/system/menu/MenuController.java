package com.ekin.system.menu;

import com.cartisan.response.GenericResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cartisan.response.ResponseUtil.success;

/**
 * @author colin
 */
@Api(tags = "系统管理：菜单")
@RestController
@RequestMapping("/system/menus")
@Validated
@Slf4j
public class MenuController {
    private final MenuAppService service;

    public MenuController(MenuAppService service) {
        this.service = service;
    }

    @ApiOperation(value = "获取所有菜单")
    @GetMapping
    public GenericResponse<List<MenuDto>> getMenuTreeList() {
        return success(service.getMenuTreeList());
    }

    @ApiOperation(value = "添加菜单")
    @PostMapping
    public GenericResponse<MenuDto> addMenu(
            @ApiParam(value = "菜单信息", required = true) @Validated @RequestBody MenuParam menuParam) {
        return success(service.addMenu(menuParam));
    }

    @ApiOperation(value = "编辑菜单")
    @PutMapping("/{id}")
    public GenericResponse<MenuDto> editMenu(
            @ApiParam(value = "菜单Id", required = true) @PathVariable Long id,
            @ApiParam(value = "菜单信息", required = true) @Validated @RequestBody MenuParam menuParam) {
        return success(service.editMenu(id, menuParam));
    }

    @ApiOperation(value = "删除菜单")
    @DeleteMapping("/{id}")
    public GenericResponse<?> removeMenu(
            @ApiParam(value = "菜单Id", required = true) @PathVariable long id) {
        service.removeMenu(id);
        return success();
    }
}
