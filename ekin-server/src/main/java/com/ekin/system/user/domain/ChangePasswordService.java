package com.ekin.system.user.domain;

import com.cartisan.constants.CodeMessage;
import com.cartisan.exceptions.CartisanException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author colin
 */
@Service
@Slf4j
public class ChangePasswordService {
    public static final String ERR_WITH_OLD_PASSWORD_SAME = "新密码不能与旧密码相同。";
    public static final String ERR_OLD_PASSWORD_NOT_CONFIRMED = "原始密码不正确。";
    private final PasswordEncoder passwordEncoder;
    private final DefaultPasswordProvider defaultPasswordProvider;

    public ChangePasswordService(PasswordEncoder passwordEncoder, DefaultPasswordProvider defaultPasswordProvider) {
        this.passwordEncoder = passwordEncoder;
        this.defaultPasswordProvider = defaultPasswordProvider;
    }

    public void changePassword(User user, String newPassword, String oldPassword) {
        if (newPassword.equals(oldPassword)) {
            log.warn("[{}] change password error: [{}]", user.getUsername(), ERR_WITH_OLD_PASSWORD_SAME);
            throw new CartisanException(CodeMessage.FAIL.fillArgs(ERR_WITH_OLD_PASSWORD_SAME));
        }

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            log.warn("[{}] change password error: [{}]", user.getUsername(), ERR_OLD_PASSWORD_NOT_CONFIRMED);
            throw new CartisanException(CodeMessage.FAIL.fillArgs(ERR_OLD_PASSWORD_NOT_CONFIRMED));
        }

        final String encodePassword = passwordEncoder.encode(newPassword);

        log.info("[{}] change password, from [{}] to [{}]", user.getUsername(), user.getPassword(), encodePassword);
        user.changePassword(encodePassword);
    }

    public void resetPassword(User user) {
        final String encodePassword =  passwordEncoder.encode(defaultPasswordProvider.generate());

        log.info("[{}] reset password, from [{}] to [{}]", user.getUsername(), user.getPassword(), encodePassword);
        user.changePassword(encodePassword);
    }
}
