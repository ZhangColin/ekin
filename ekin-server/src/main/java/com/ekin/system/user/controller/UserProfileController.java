package com.ekin.system.user.controller;

import com.ekin.system.user.application.UserProfileAppService;
import com.ekin.system.user.request.ChangePasswordCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.cartisan.responses.ResponseUtil.success;

/**
 * @author colin
 */
@Api(tags = "系统管理：个人中心")
@RestController
@RequestMapping("/system/users/profile")
@Validated
@Slf4j
public class UserProfileController {

    private final UserProfileAppService service;

    public UserProfileController(UserProfileAppService service) {
        this.service = service;
    }

    @ApiOperation(value = "修改密码")
    @PutMapping("/changePassword")
    public ResponseEntity<?> changePassword(
            @ApiParam(value = "修改密码命令", required = true) @Validated @RequestParam ChangePasswordCommand changePasswordCommand) {
        service.changePassword(changePasswordCommand);

        return success();
    }
}
