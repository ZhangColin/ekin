package com.ekin.system.role;

import com.cartisan.dto.PageResult;
import com.cartisan.response.GenericResponse;
import com.ekin.system.menu.MenuDto;
import com.ekin.system.role.request.RoleParam;
import com.ekin.system.role.request.RoleQuery;
import com.ekin.system.role.response.RoleDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cartisan.response.ResponseUtil.success;

/**
 * @author colin
 */
@Api(tags = "系统管理：角色")
@RestController
@RequestMapping("/system/roles")
@Validated
@Slf4j
public class RoleController {
    private final RoleAppService service;

    public RoleController(RoleAppService service) {
        this.service = service;
    }

//    @ApiOperation(value = "搜索角色")
//    @GetMapping("/search")
//    public GenericResponse<PageResult<RoleDetailDto>> searchRoles(
//            @ApiParam(value = "查询参数") RoleQuery roleQuery,
//            @PageableDefault Pageable pageable) {
//        return success(service.searchRoles(roleQuery, pageable));
//    }
    @ApiOperation(value = "获取所有角色")
    @GetMapping("/search")
    public GenericResponse<List<RoleDto>> getMenuTreeList() {
        return success(service.getRoleTreeList());
    }
//
//    @ApiOperation(value = "获取所有启用的角色")
//    @GetMapping
//    public GenericResponse<List<RoleDto>> getAllEnableRoles(){
//        return success(service.getAllEnableRoles());
//    }

    @ApiOperation(value = "获取角色")
    @GetMapping("/{id}")
    public GenericResponse<RoleDto> getRole(@ApiParam(value = "角色Id", required = true) @PathVariable Long id){
        return success(service.getRole(id));
    }

    @ApiOperation(value = "添加角色")
    @PostMapping
    public GenericResponse<RoleDto> addRole(
            @ApiParam(value = "角色信息", required = true) @Validated @RequestBody RoleParam roleParam) {
        return success(service.addRole(roleParam));
    }

    @ApiOperation(value = "编辑角色")
    @PutMapping("/{id}")
    public GenericResponse<RoleDto> editRole(
            @ApiParam(value = "角色Id", required = true) @PathVariable Long id,
            @ApiParam(value = "角色信息", required = true) @Validated @RequestBody RoleParam roleParam) {
        return success(service.editRole(id, roleParam));
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{id}")
    public GenericResponse<?> removeRole(
            @ApiParam(value = "角色Id", required = true) @PathVariable long id) {
        service.removeRole(id);
        return success();
    }
//
//    @ApiOperation(value = "分配菜单")
//    @PutMapping("/{id}/menus")
//    public GenericResponse<?> assignMenus(
//            @ApiParam(value = "角色Id", required = true) @PathVariable Long id,
//            @ApiParam(value = "菜单Ids", required = true) @Validated @RequestBody AssignMenusCommand command) {
//        service.assignMenus(id, command.getMenuIds());
//        return success();
//    }
//
//    @ApiOperation(value = "分配资源")
//    @PutMapping("/{id}/resources")
//    public GenericResponse<?> assignResources(
//            @ApiParam(value = "角色Id", required = true) @PathVariable Long id,
//            @ApiParam(value = "资源Ids", required = true) @Validated @RequestBody AssignResourcesCommand command) {
//        service.assignResources(id, command.getResourceIds());
//        return success();
//    }
//
//    @ApiOperation(value = "启用角色")
//    @PutMapping("/{id}/enable")
//    public GenericResponse<?> enable(
//            @ApiParam(value = "角色Id", required = true) @PathVariable Long id) {
//        service.enable(id);
//        return success();
//    }
//
//    @ApiOperation(value = "禁用角色")
//    @PutMapping("/{id}/disable")
//    public GenericResponse<?> disable(
//            @ApiParam(value = "角色Id", required = true) @PathVariable Long id) {
//        service.disable(id);
//        return success();
//    }
}
