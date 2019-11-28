package com.ekin.system.controllers;

import com.cartisan.dtos.PageResult;
import com.cartisan.responses.GenericResponse;
import com.ekin.system.dtos.RoleDto;
import com.ekin.system.dtos.RoleInfo;
import com.ekin.system.params.RoleParam;
import com.ekin.system.services.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cartisan.responses.GenericResponse.success;

/**
 * @author colin
 */
@Api(tags = "RoleController")
@RestController
@RequestMapping("/system/roles")
public class RoleController {
    private final RoleService service;

    @Autowired
    public RoleController(RoleService service) {
        this.service = service;
    }

    @ApiOperation(value = "搜索角色")
    @GetMapping("/search/{currentPage}/{pageSize}")
    public GenericResponse<PageResult<RoleDto>> searchRoles(
            @ApiParam(value = "查询角色名") @RequestParam(required = false) String name,
            @ApiParam(value = "页码", required = true) @PathVariable Integer currentPage,
            @ApiParam(value = "每页记录数", required = true) @PathVariable Integer pageSize) {
        return success(service.searchRoles(name, currentPage, pageSize));
    }

    @ApiOperation(value = "获取所有角色")
    @GetMapping
    public GenericResponse<List<RoleInfo>> getAllRoles(){
        return success(service.getAllRoles());
    }

    @ApiOperation(value = "获取角色")
    @GetMapping("/{id}")
    public GenericResponse<RoleDto> getRole(@ApiParam(value = "角色Id", required = true) @PathVariable Long id){
        return success(service.getRole(id));
    }

    @ApiOperation(value = "添加角色")
    @PostMapping
    public GenericResponse addRole(
            @ApiParam(value = "角色信息", required = true) @Validated @RequestBody RoleParam roleParam) {
        service.addRole(roleParam);

        return success();
    }

    @ApiOperation(value = "更新角色")
    @PutMapping("/{id}")
    public GenericResponse editRole(
            @ApiParam(value = "角色Id", required = true) @PathVariable Long id,
            @ApiParam(value = "角色信息", required = true) @Validated @RequestBody RoleParam roleParam) {
        service.editRole(id, roleParam);

        return success();
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/{id}")
    public GenericResponse removeRole(
            @ApiParam(value = "角色Id", required = true) @PathVariable long id) {
        service.removeRole(id);

        return success();
    }

    @ApiOperation(value = "获取角色下的权限Id")
    @GetMapping("/{id}/permissions")
    public GenericResponse<List<String>> getRolePermissions(@ApiParam(value = "角色Id", required = true) @PathVariable Long id){
        return success(service.getRolePermissions(id));
    }

    @ApiOperation(value = "分配权限")
    @PostMapping("/{id}/permissions")
    public GenericResponse addRole(
            @ApiParam(value = "角色Id", required = true) @PathVariable Long id,
            @ApiParam(value = "权限Ids", required = true) @Validated @RequestBody List<Long> permissionIds) {
        service.assignPermissions(id, permissionIds);

        return success();
    }
}
