package com.ekin.security.application;

import com.cartisan.util.AesUtil;
import com.ekin.security.CurrentUser;
import com.ekin.security.LoginService;
import com.ekin.system.user.UserRepository;
import com.ekin.system.user.domain.User;
import com.ekin.system.user.mapper.UserQueryMapper;
import com.ekin.system.user.request.LoginCommand;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.cartisan.util.AssertionUtil.requirePresent;

/**
 * @author colin
 */
@Service
public class LoginAppService{
    private final LoginService loginService;
    private final CurrentUser currentUser;
    private final UserQueryMapper userQueryMapper;
    private final UserRepository userRepository;

    public LoginAppService(LoginService loginService, CurrentUser currentUser, UserQueryMapper userQueryMapper, UserRepository userRepository) {
        this.loginService = loginService;
        this.currentUser = currentUser;
        this.userQueryMapper = userQueryMapper;
        this.userRepository = userRepository;
    }

    public Map<String, Object> login(LoginCommand loginCommand) {
        final String token = loginService.login(loginCommand.getUsername(), AesUtil.aesDecode(loginCommand.getPassword()));

        HashMap<String, Object> userInfo = new HashMap<>();

        User user = requirePresent(userRepository.findById(currentUser.getUserId()));

        userInfo.put("token", token);
        userInfo.put("refresh_token", "");
        userInfo.put("avatar", user.getAvatar());
        userInfo.put("username", user.getUsername());
        userInfo.put("nickname", user.getNickname());
        userInfo.put("id", user.getId());
        userInfo.put("super", user.getUsername().equals("admin"));

        return userInfo;
    }

    public void logout() {
        loginService.logoutByUsername(currentUser.getUsername());
    }

    public Map<String, Object> info() {
        Map<String, Object> info = new HashMap<>();
        info.put("userId", currentUser.getUserId().toString());
        info.put("roles", userQueryMapper.getUserAuthorities(currentUser.getUserId()));
        info.put("menus", userQueryMapper.getUserMenus(currentUser.getUserId()));
        info.put("avatar", requirePresent(userRepository.findById(currentUser.getUserId())).getAvatar());

        return info;
    }

}
