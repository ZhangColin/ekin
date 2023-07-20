package com.ekin.system.user.controller;

import com.cartisan.constant.CodeMessage;
import com.cartisan.exception.CartisanException;
import com.cartisan.response.GenericResponse;
import com.ekin.system.user.application.LoginAppService;
import com.ekin.system.user.request.LoginCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.cartisan.response.ResponseUtil.success;

/**
 * @author colin
 */
@Api(tags = "系统管理：登录")
@RestController
@RequestMapping("/system")
@Validated
@Slf4j
public class LoginController {
    private final LoginAppService loginAppService;

    public LoginController(LoginAppService loginAppService) {
        this.loginAppService = loginAppService;
    }

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public GenericResponse<Map<String, Object>> login(
            @ApiParam(value = "登录信息", required = true) @Validated @RequestBody LoginCommand loginCommand) {
        return success(loginAppService.login(loginCommand));
    }

    @ApiOperation(value = "退出")
    @PostMapping("/logout")
    public GenericResponse logout() {
//        throw new CartisanException(CodeMessage.FAIL.fillArgs("logout error"));
        loginAppService.logout();
        return success();
    }

    @ApiOperation(value = "登录用户信息")
    @GetMapping("/user/info")
    public GenericResponse<Map<String, Object>> info() {
        return success(loginAppService.info());
    }
}
