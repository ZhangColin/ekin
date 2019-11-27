package com.ekin.systemcontext.services;

import com.cartisan.common.exceptions.CartisanException;
import com.cartisan.common.utils.RedisUtil;
import com.ekin.systemcontext.constants.LoginKey;
import com.ekin.systemcontext.constants.SystemCodeMessage;
import com.ekin.systemcontext.domains.User;
import com.ekin.systemcontext.dtos.UserDto;
import com.ekin.systemcontext.params.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * @author colin
 */
@Service
public class LoginService {
    private final UserService userService;

    private final RedisUtil redisUtil;

    @Autowired
    public LoginService(UserService userService, RedisUtil redisUtil) {
        this.userService = userService;
        this.redisUtil = redisUtil;
    }

    public String login(LoginParam loginParam) {
        final Optional<User> userOptional = userService.findByUserName(loginParam.getUsername());

        final User user = userOptional
                .orElseThrow(() -> new CartisanException(SystemCodeMessage.ERROR_USERNAME_OR_PASSWORD));

        if (!user.valid(loginParam.getPassword())) {
            throw new CartisanException(SystemCodeMessage.ERROR_USERNAME_OR_PASSWORD);
        }


        final String token = UUID.randomUUID().toString().replace("-", "");

        redisUtil.set(LoginKey.token, token, UserDto.convertFrom(user));

        return token;

    }

    public void logout(String token) {
        // 清除 token 缓存
        redisUtil.delete(LoginKey.token, token);

        // 清除角色缓存
    }

    public UserDto getUserByToken(String token) {
        return redisUtil.get(LoginKey.token, token);
    }
}
