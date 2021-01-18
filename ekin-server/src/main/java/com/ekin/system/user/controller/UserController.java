package com.ekin.system.user.controller;

import com.cartisan.dtos.PageResult;
import com.ekin.system.user.application.UserAppService;
import com.ekin.system.user.request.AssignOrganizationsCommand;
import com.ekin.system.user.request.AssignRolesCommand;
import com.ekin.system.user.request.CreateAccountCommand;
import com.ekin.system.user.request.UserQuery;
import com.ekin.system.user.response.UserDetailDto;
import com.ekin.system.user.response.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cartisan.responses.ResponseUtil.success;

/**
 * @author colin
 */
@Api(tags = "系统管理：用户")
@RestController
@RequestMapping("/system/users")
@Validated
@Slf4j
public class UserController {
    private final UserAppService service;

    public UserController(UserAppService service) {
        this.service = service;
    }

    @ApiOperation(value = "搜索用户")
    @GetMapping("/search")
//    @PreAuthorize("hasAuthority('test')")
    public ResponseEntity<PageResult<UserDto>> searchUsers(
            @ApiParam(value = "查询参数") UserQuery userQuery,
            @PageableDefault Pageable pageable) {
        return success(service.searchUsers(userQuery, pageable));
    }

    @ApiOperation(value = "获取所有正常用户")
    @GetMapping
    public ResponseEntity<List<UserDto>> findAllNormalUsers() {
        return success(service.findAllNormalUsers());
    }

    @ApiOperation(value = "获取用户")
    @GetMapping("/{id}")
    public ResponseEntity<UserDetailDto> getUser(
            @ApiParam(value = "用户Id", required = true) @PathVariable Long id) {
        return success(service.getUser(id));
    }

    @ApiOperation(value = "创建用户账号")
    @PostMapping
    public ResponseEntity<?> createAccount(
            @ApiParam(value = "账号信息", required = true) @Validated @RequestBody CreateAccountCommand createAccountCommand) {
        return success(service.createAccount(createAccountCommand));
    }

    @ApiOperation(value = "分配角色")
    @PutMapping("/{userId}/assignRoles")
    public ResponseEntity<?> assignRoles(
            @ApiParam(value = "用户Id", required = true) @PathVariable Long userId,
            @ApiParam(value = "分配角色", required = true) @RequestBody AssignRolesCommand command) {
        service.assignRoles(userId, command);
        return success();
    }

    @ApiOperation(value = "分配组织")
    @PutMapping("/{userId}/assignOrganization")
    public ResponseEntity<?> assignOrganizations(
            @ApiParam(value = "用户Id", required = true) @PathVariable Long userId,
            @ApiParam(value = "分配组织", required = true) @RequestBody AssignOrganizationsCommand command) {
        service.assignOrganization(userId, command);
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

    @ApiOperation(value = "重置密码")
    @PutMapping("/{userId}/resetPassword")
    public ResponseEntity<?> resetPassword(
            @ApiParam(value = "用户Id", required = true) @PathVariable Long userId) {
        service.resetPassword(userId);

        return success();
    }
}
