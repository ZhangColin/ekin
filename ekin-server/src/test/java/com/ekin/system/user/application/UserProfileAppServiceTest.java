package com.ekin.system.user.application;

import com.cartisan.security.LoginService;
import com.cartisan.utils.AesUtil;
import com.ekin.security.CurrentUser;
import com.ekin.system.user.UserRepository;
import com.ekin.system.user.domain.ChangePasswordService;
import com.ekin.system.user.domain.User;
import com.ekin.system.user.request.ChangePasswordCommand;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static com.ekin.system.user.UserFixture.userOf;
import static org.mockito.Mockito.*;

public class UserProfileAppServiceTest {

    private ChangePasswordService changePasswordService;
    private LoginService loginService;
    private UserRepository userRepository;
    private CurrentUser currentUser;
    private UserProfileAppService userProfileAppService;
    private User user;

    @Before
    public void setUp() throws Exception {
        changePasswordService = mock(ChangePasswordService.class);
        loginService = mock(LoginService.class);
        userRepository = mock(UserRepository.class);
        currentUser = mock(CurrentUser.class);
        userProfileAppService = new UserProfileAppService(changePasswordService, loginService, userRepository, currentUser);

        user = userOf();
    }

    @Test
    public void when_change_password_success_then_current_user_logout() {
        // given
        when(currentUser.getUserId()).thenReturn(user.getId());
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        ChangePasswordCommand changePasswordCommand = new ChangePasswordCommand();
        changePasswordCommand.setNewPassword("xPsc0JS6uGeYx7O0BquWQw==");
        changePasswordCommand.setOldPassword("8tK2r6Rrfsg9IKTpYqtMug==");

        // when
        userProfileAppService.changePassword(changePasswordCommand);

        // then
        verify(changePasswordService).changePassword(user,
                AesUtil.aesDecode(changePasswordCommand.getNewPassword()),
                AesUtil.aesDecode(changePasswordCommand.getOldPassword()));
        verify(loginService).logoutByUsername(user.getUsername());
    }
}