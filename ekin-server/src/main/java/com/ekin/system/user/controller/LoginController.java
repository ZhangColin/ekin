package com.ekin.system.user.controller;

import com.ekin.system.permission.mapper.PermissionQueryMapper;
import com.ekin.system.user.application.LoginAppService;
import com.ekin.system.user.request.LoginParam;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
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
@RestController
@RequestMapping("/system")
public class LoginController {
    private final LoginAppService loginAppService;

    private final PermissionQueryMapper permissionQueryMapper;

    @Autowired
    public LoginController(LoginAppService loginAppService, PermissionQueryMapper permissionQueryMapper) {
        this.loginAppService = loginAppService;
        this.permissionQueryMapper = permissionQueryMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @ApiParam(value = "登录信息", required = true) @Validated @RequestBody LoginParam loginParam,
            HttpServletRequest request) {
        final String token = loginAppService.login(loginParam);
        HashMap<String, Object> data = new HashMap<>();
        data.put("token", token);

//        LogExeManager.getInstance().executeLogTask(LogTaskFactory.loginLog(0L,
//                IpUtil.getIpFromRequest(WebUtils.toHttp(request)),
//                "/login", "POST", "", true));

        return success(data);
    }

    @PostMapping("/logout")
    public ResponseEntity logout() {
//        loginAppService.logout(token);
        return success();
    }

    @GetMapping("/user/info")
    public ResponseEntity info() {
//        final UserDto user = loginService.getUserByToken(token);
//        final UserDto user = new UserDto();
//        final List<String> roles = user.getRoleCodes();
//        final List<String> permissions = permissionQueryMapper.getPermissionCodesByRoleCodes(roles);

        Map<String, Object> info = new HashMap<>();
        info.put("roles", asList("admin"));
        info.put("permissions", asList("admin"));
        info.put("name", "colin");
        info.put("avatar", "");

        return success(info);
    }
}
