package com.ekin.system.user.application;

import com.cartisan.security.LoginService;
import com.cartisan.utils.AesUtil;
import com.ekin.system.user.request.LoginCommand;
import org.springframework.stereotype.Service;

/**
 * @author colin
 */
@Service
public class LoginAppService{
    private final LoginService loginService;

    public LoginAppService(LoginService loginService) {
        this.loginService = loginService;
    }

    public String login(LoginCommand loginCommand) {
        return loginService.login(loginCommand.getUsername(), AesUtil.aesDecode(loginCommand.getPassword()));
    }

    public void logout(String token) {
        loginService.logout(token);
    }

}
