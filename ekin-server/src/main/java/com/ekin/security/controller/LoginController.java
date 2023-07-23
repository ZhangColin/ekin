package com.ekin.security.controller;

import com.cartisan.response.GenericResponse;
import com.ekin.security.application.LoginAppService;
import com.ekin.system.user.request.LoginCommand;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@Validated
@Slf4j
public class LoginController {
    private final LoginAppService loginAppService;
    private ObjectMapper objectMapper;

    public LoginController(LoginAppService loginAppService, ObjectMapper objectMapper) {
        this.loginAppService = loginAppService;
        this.objectMapper = objectMapper;
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
        loginAppService.logout();
        return success();
    }

    @ApiOperation(value = "登录用户信息")
    @GetMapping("/user/info")
    public GenericResponse<Object> info() throws JsonProcessingException {

        return success(objectMapper.readValue("{\n" +
                "    \"userInfo\": {\n" +
                "        \"id\": 1,\n" +
                "        \"username\": \"admin\",\n" +
                "        \"nickname\": \"Admin\",\n" +
                "        \"avatar\": \"https://demo.buildadmin.com/static/images/avatar.png\",\n" +
                "        \"last_login_time\": \"2023-07-17 21:30:24\",\n" +
                "        \"token\": \"5fe78d04-c686-4500-886c-473aaa4080c3\",\n" +
                "        \"refresh_token\": \"\"\n" +
                "    },\n" +
                "    \"routePath\": \"/\"\n" +
                "}", Object.class));
//        return success(loginAppService.info());
    }
}
