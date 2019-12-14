package com.ekin.system.controller;

import com.ekin.system.appservice.user.response.UserDto;
import com.ekin.system.appservice.user.request.LoginParam;
import com.ekin.system.queries.PermissionQueryMapper;
import com.ekin.system.appservice.user.LoginService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cartisan.responses.ResponseUtil.success;

/**
 * @author colin
 */
@RestController
@RequestMapping("/system")
public class LoginController {
    private final LoginService loginService;

    private final PermissionQueryMapper permissionQueryMapper;

    @Autowired
    public LoginController(LoginService loginService, PermissionQueryMapper permissionQueryMapper) {
        this.loginService = loginService;
        this.permissionQueryMapper = permissionQueryMapper;
    }

    @PostMapping("/login")
    public ResponseEntity login(
            @ApiParam(value = "登录信息", required = true) @Validated @RequestBody LoginParam loginParam,
            HttpServletRequest request) {
        final String token = loginService.login(loginParam);
        HashMap<String, Object> data = new HashMap<>();
        data.put("token", token);

//        LogExeManager.getInstance().executeLogTask(LogTaskFactory.loginLog(0L,
//                IpUtil.getIpFromRequest(WebUtils.toHttp(request)),
//                "/login", "POST", "", true));

        return success(data);
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestHeader(name = "X-Token") String token) {
        loginService.logout(token);
        return success();
    }

    @GetMapping("/user/info")
    public ResponseEntity info(@RequestHeader(name = "X-Token") String token) {
        final UserDto user = loginService.getUserByToken(token);
        final List<String> roles = user.getRoleCodes();
        final List<String> permissions = permissionQueryMapper.getPermissionCodesByRoleCodes(roles);

        Map<String, Object> info = new HashMap<>();
        info.put("roles", roles);
        info.put("permissions", permissions);
        info.put("name", user.getUsername());
        info.put("avatar", user.getAvatar());

        return success(info);
    }
}
