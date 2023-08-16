package com.ekin.system.user.domain;

import com.cartisan.constant.CodeMessage;
import com.cartisan.exception.CartisanException;
import com.cartisan.util.SnowflakeIdWorker;
import com.ekin.system.user.UserRepository;
import com.google.common.base.Strings;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author colin
 */
@Service
public class RegisterService {
    private final UserRepository userRepository;
    private final SnowflakeIdWorker idWorker;
    private final DefaultPasswordProvider defaultPasswordProvider;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(UserRepository userRepository, SnowflakeIdWorker idWorker,
                           DefaultPasswordProvider defaultPasswordProvider, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.idWorker = idWorker;
        this.defaultPasswordProvider = defaultPasswordProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(String username, String phone, String email, String nickName) {
        if (Strings.isNullOrEmpty(username) && Strings.isNullOrEmpty(phone) && Strings.isNullOrEmpty(email)) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs("账号、手机、邮箱至少提供一个。"));
        }

        username = ensureUsername(username, phone, email);
        nickName = ensureNickname(nickName, username);

        if (userRepository.existsByUsername(username)) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs("账号已被占用。"));
        }

        if (!Strings.isNullOrEmpty(phone) && userRepository.existsByPhone(phone)) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs("手机号已被占用。"));
        }

        if (!Strings.isNullOrEmpty(email) && userRepository.existsByEmail(email)) {
            throw new CartisanException(CodeMessage.VALIDATE_ERROR.fillArgs("邮箱已被占用。"));
        }

        final String encodePassword = passwordEncoder.encode(defaultPasswordProvider.generate());

        return new User(idWorker.nextId(), username, phone, email, encodePassword, nickName);
    }

    private String ensureUsername(String username, String phone, String email) {
        if (!Strings.isNullOrEmpty(username)) {
            return username;
        }
        if (!Strings.isNullOrEmpty(phone)) {
            return phone;
        }
        return email;
    }

    private String ensureNickname(String nickname, String username) {
        if (!Strings.isNullOrEmpty(nickname)) {
            return nickname;
        }
        return username;
    }
}
