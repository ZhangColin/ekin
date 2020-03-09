package com.ekin.system.user.controller;

import com.ekin.system.user.application.LoginAppService;
import com.ekin.system.user.request.LoginCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.cartisan.responses.ResponseUtil.success;
import static java.util.Arrays.asList;

/**
 * @author colin
 */
@Api(tags = "系统管理：登录")
@RestController
@RequestMapping("/system")
public class LoginController {
    private final LoginAppService loginAppService;

    public LoginController(LoginAppService loginAppService) {
        this.loginAppService = loginAppService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @ApiParam(value = "登录信息", required = true) @Validated @RequestBody LoginCommand loginCommand) {
        return success(loginAppService.login(loginCommand));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        loginAppService.logout();
        return success();
    }

    @GetMapping("/user/info")
    public ResponseEntity<?> info() {
        return success(loginAppService.info());
    }
}
