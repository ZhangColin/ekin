package com.ekin.system.user.controller;

import com.cartisan.dtos.PageResult;
import com.ekin.system.user.request.AssignDepartmentsCommand;
import com.ekin.system.user.request.AssignRolesCommand;
import com.ekin.system.user.response.UserDetailDto;
import com.ekin.system.user.request.CreateAccountCommand;
import com.ekin.system.user.request.UserQuery;
import com.ekin.system.user.application.UserAppService;
import com.ekin.system.user.response.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.cartisan.responses.ResponseUtil.success;

/**
 * @author colin
 */
@Api(tags = "UserController")
@RestController
@RequestMapping("/system/users")
public class UserController {
    private final UserAppService service;

    @Autowired
    public UserController(UserAppService service) {
        this.service = service;
    }

    @ApiOperation(value = "获取用户")
    @GetMapping("/{id}")
    public ResponseEntity<UserDetailDto> getUser(
            @ApiParam(value = "用户Id", required = true) @PathVariable Long id) {
        return success(service.getUser(id));
    }

    @ApiOperation(value = "搜索用户")
    @GetMapping("/search")
    public ResponseEntity<PageResult<UserDto>> searchUsers(
            @ApiParam(value = "查询参数") UserQuery userQuery,
            @PageableDefault Pageable pageable) {
        return success(service.searchUsers(userQuery, pageable));
    }

    @ApiOperation(value = "创建用户账号")
    @PostMapping
    public ResponseEntity<?> createAccount(
            @ApiParam(value = "账号信息", required = true) @Validated @RequestBody CreateAccountCommand createAccountCommand) {
        service.createAccount(createAccountCommand);

        return success();
    }

    @ApiOperation(value = "分配角色")
    @PutMapping("/{userId}/assignRoles")
    public ResponseEntity<?> assignRoles(
            @ApiParam(value = "用户Id", required = true) @PathVariable Long userId,
            @ApiParam(value = "分配的角色编码", required = true) @RequestBody AssignRolesCommand command) {
        service.assignRoles(userId, command);
        return success();
    }

    @ApiOperation(value = "分配组织")
    @PutMapping("/{userId}/assignDepartments")
    public ResponseEntity<?> assignDepartments(
            @ApiParam(value = "用户Id", required = true) @PathVariable Long userId,
            @ApiParam(value = "分配的组织", required = true) @RequestBody AssignDepartmentsCommand command) {
        service.assignDepartments(userId, command);
        return success();
    }


    @ApiOperation(value = "禁用用户")
    @PutMapping("/{userId}/disable")
    public ResponseEntity<?> disableUser(
            @ApiParam(value = "用户Id", required = true) @PathVariable Long userId) {
        service.disable(userId);

        return success();
    }

    @ApiOperation(value = "启用用户")
    @PutMapping("/{userId}/enable")
    public ResponseEntity<?> enableUser(
            @ApiParam(value = "用户Id", required = true) @PathVariable Long userId) {
        service.enable(userId);

        return success();
    }

    @ApiOperation(value = "修改密码")
    @PutMapping("/{id}/password")
    public ResponseEntity changePassword(
            @ApiParam(value = "用户Id", required = true) @PathVariable Long id,
            @ApiParam(value = "密码", required = true) @Validated @RequestParam String password) {
        service.changePassword(id, password);

        return success();
    }
}
