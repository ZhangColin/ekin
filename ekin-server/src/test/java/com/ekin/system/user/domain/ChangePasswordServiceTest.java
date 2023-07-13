package com.ekin.system.user.domain;


import com.cartisan.exception.CartisanException;
import com.ekin.system.user.UserFixture;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ChangePasswordServiceTest {
    private ChangePasswordService changePasswordService;
    private PasswordEncoder passwordEncoder;
    private DefaultPasswordProvider defaultPasswordProvider;
    private User user;

    @Before
    public void setUp() {
        passwordEncoder = mock(PasswordEncoder.class);
        defaultPasswordProvider = mock(DefaultPasswordProvider.class);
        changePasswordService = new ChangePasswordService(passwordEncoder, defaultPasswordProvider);
        user = UserFixture.userOf();
    }

    @Test
    public void when_new_password_same_old_password_then_throw_exception() {
        assertThatThrownBy(() -> changePasswordService.changePassword(user, "123456", "123456"))
                .isInstanceOf(CartisanException.class)
                .hasMessage(ChangePasswordService.ERR_WITH_OLD_PASSWORD_SAME);
    }

    @Test
    public void when_old_password_not_confirmed_then_throw_exception() {
        // given
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // when then
        assertThatThrownBy(() -> changePasswordService.changePassword(user, "123456", "abcdef"))
                .isInstanceOf(CartisanException.class)
                .hasMessage(ChangePasswordService.ERR_OLD_PASSWORD_NOT_CONFIRMED);
    }

    @Test
    public void should_change_password_success() {
        // given
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(passwordEncoder.encode(anyString())).thenReturn("encodeNewPassword");

        // when
        changePasswordService.changePassword(user, "123456", "abcdef");

        // then
        assertThat(user.getPassword()).isEqualTo("encodeNewPassword");
    }

    @Test
    public void should_reset_password_success() {
        // given
        when(defaultPasswordProvider.generate()).thenReturn("123456");
        when(passwordEncoder.encode(anyString())).thenReturn("encodeNewPassword");

        // when
        changePasswordService.resetPassword(user);

        // then
        assertThat(user.getPassword()).isEqualTo("encodeNewPassword");
    }
}