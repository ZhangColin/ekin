package com.ekin.system.user.controller;

import com.cartisan.dtos.PageResult;
import com.ekin.system.user.response.UserDto;
import com.ekin.system.user.request.UserParam;
import com.ekin.system.user.request.SearchUser;
import com.ekin.system.user.application.UserAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<UserDto> getUser(
            @ApiParam(value = "用户Id", required = true) @PathVariable Long id) {
        return success(service.getUser(id));
    }

    @ApiOperation(value = "搜索用户")
    @PostMapping("/search/{currentPage}/{pageSize}")
    public ResponseEntity<PageResult<UserDto>> searchUsers(
            @ApiParam(value = "查询参数") @RequestBody(required = false) SearchUser searchUser,
            @ApiParam(value = "页码", required = true) @PathVariable Integer currentPage,
            @ApiParam(value = "每页记录数", required = true) @PathVariable Integer pageSize) {
        return success(service.searchUsers(searchUser, currentPage, pageSize));
    }

    @ApiOperation(value = "添加用户")
    @PostMapping
    public ResponseEntity addUser(
            @ApiParam(value = "用户信息", required = true) @Validated @RequestBody UserParam userParam) {
        service.addUser(userParam);

        return success();
    }

    @ApiOperation(value = "更新用户")
    @PutMapping("/{id}")
    public ResponseEntity editUser(
            @ApiParam(value = "用户Id", required = true) @PathVariable Long id,
            @ApiParam(value = "用户信息", required = true) @Validated @RequestBody UserParam userParam) {
        service.editUser(id, userParam);

        return success();
    }

    @ApiOperation(value = "删除用户")
    @DeleteMapping("/{id}")
    public ResponseEntity removeUser(
            @ApiParam(value = "用户Id", required = true) @PathVariable long id) {
        service.removeUser(id);

        return success();
    }

    @ApiOperation(value = "冻结用户")
    @PutMapping("/{id}/frozen")
    public ResponseEntity frozenUser(
            @ApiParam(value = "用户Id", required = true) @PathVariable Long id) {
        service.frozen(id);

        return success();
    }

    @ApiOperation(value = "解冻用户")
    @PutMapping("/{id}/unFrozen")
    public ResponseEntity unFrozenUser(
            @ApiParam(value = "用户Id", required = true) @PathVariable Long id) {
        service.unFrozen(id);

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
