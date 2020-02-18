package com.ekin.system.user;

import com.cartisan.exceptions.CartisanException;
import com.cartisan.security.JwtTokenProvider;
import com.ekin.system.user.application.LoginAppService;
import com.ekin.system.user.request.LoginCommand;
import com.ekin.system.user.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginAppServiceTest {

    private JwtTokenProvider tokenProvider;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private LoginCommand loginCommand;
    private User user;
    private LoginAppService loginAppService;

    @Before
    public void setUp() {
        tokenProvider = mock(JwtTokenProvider.class);
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);

        loginAppService = new LoginAppService(userRepository, tokenProvider, passwordEncoder);

        loginCommand = new LoginCommand();
        loginCommand.setUsername("colin");
        loginCommand.setPassword("123456");

        user = new User(1L, "colin", "", "", "123456", "");
    }

    @Test
    public void should_be_login_success() {
        // given
        when(tokenProvider.generateToken("colin")).thenReturn("colinToken");
        when(passwordEncoder.matches("123456", "123456")).thenReturn(true);
        when(userRepository.findByUsername("colin")).thenReturn(Optional.of(user));

        // when
        final String token = loginAppService.login(loginCommand);

        // then
        assertThat(token).isEqualTo("colinToken");
    }

    @Test
    public void when_user_not_found_then_throw_exception() {
        // given
        when(userRepository.findByUsername("colin")).thenReturn(Optional.empty());

        // then
        assertThatThrownBy(()-> loginAppService.login(loginCommand))
            .isInstanceOf(CartisanException.class)
            .hasMessage("用户名或密码不正确");
    }

    @Test
    public void when_password_not_valid_then_throw_exception() {
        // given
        when(passwordEncoder.matches("123456", "123456")).thenReturn(false);
        when(userRepository.findByUsername("colin")).thenReturn(Optional.of(user));

        // then
        assertThatThrownBy(()-> loginAppService.login(loginCommand))
            .isInstanceOf(CartisanException.class)
            .hasMessage("用户名或密码不正确");
    }
}