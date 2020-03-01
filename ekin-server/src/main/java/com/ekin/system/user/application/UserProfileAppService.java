package com.ekin.system.user.application;

import com.cartisan.security.LoginService;
import com.cartisan.utils.AesUtil;
import com.ekin.security.CurrentUser;
import com.ekin.system.user.UserRepository;
import com.ekin.system.user.domain.ChangePasswordService;
import com.ekin.system.user.domain.User;
import com.ekin.system.user.request.ChangePasswordCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.cartisan.utils.AssertionUtil.requirePresent;


/**
 * @author colin
 */
@Service
@Slf4j
public class UserProfileAppService {
    private final LoginService loginService;
    private final ChangePasswordService changePasswordService;
    private final UserRepository repository;
    private final CurrentUser currentUser;

    public UserProfileAppService(
            ChangePasswordService changePasswordService,
            LoginService loginService,
            UserRepository repository,
            CurrentUser currentUser) {
        this.loginService = loginService;
        this.repository = repository;
        this.changePasswordService = changePasswordService;
        this.currentUser = currentUser;
    }

    @Transactional(rollbackOn = Exception.class)
    public void changePassword(ChangePasswordCommand changePasswordCommand) {
        final User user = requirePresent(repository.findById(currentUser.getUserId()));

        changePasswordService.changePassword(user,
                AesUtil.aesDecode(changePasswordCommand.getNewPassword()),
                AesUtil.aesDecode(changePasswordCommand.getOldPassword()));

        loginService.logoutByUsername(user.getUsername());

        repository.save(user);
    }
}
