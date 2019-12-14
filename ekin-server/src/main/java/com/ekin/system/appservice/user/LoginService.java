package com.ekin.system.appservice.user;

import com.cartisan.exceptions.CartisanException;
import com.cartisan.utils.RedisUtil;
import com.ekin.system.constant.LoginKey;
import com.ekin.system.constant.SystemCodeMessage;
import com.ekin.system.domain.user.User;
import com.ekin.system.appservice.user.response.UserDto;
import com.ekin.system.appservice.user.request.LoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * @author colin
 */
@Service
public class LoginService {
    private final UserAppService userAppService;

    private final RedisUtil redisUtil;

    @Autowired
    public LoginService(UserAppService userAppService, RedisUtil redisUtil) {
        this.userAppService = userAppService;
        this.redisUtil = redisUtil;
    }

    public String login(LoginParam loginParam) {
        final Optional<User> userOptional = userAppService.findByUserName(loginParam.getUsername());

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
