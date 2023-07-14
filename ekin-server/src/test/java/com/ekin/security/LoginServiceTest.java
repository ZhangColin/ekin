package com.ekin.security;

import com.cartisan.exception.CartisanException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LoginServiceTest {
    private JwtTokenProvider tokenProvider;
    private UserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;
    private LoginService loginService;
    private HashOperations<String, String, Object> hashOperations;

    private UserDetails userDetails;

    @Before
    public void setUp() {
        tokenProvider = mock(JwtTokenProvider.class);
        userDetailsService = mock(UserDetailsService.class);
        passwordEncoder = mock(PasswordEncoder.class);
        hashOperations = mock(HashOperations.class);

        loginService = new LoginService(userDetailsService, tokenProvider, passwordEncoder, hashOperations);
        userDetails = mock(UserDetails.class);
    }

    @Test
    public void should_be_login_success() {
        // given
        when(tokenProvider.generateToken(any())).thenReturn("colinToken");
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(userDetails.getPassword()).thenReturn("123456");
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);

        // when
        final String token = loginService.login("colin", "123456");

        // then
        assertThat(token).isEqualTo("colinToken");
    }

    @Test
    public void when_user_not_found_then_throw_exception() {
        // given
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(null);

        // then
        assertThatThrownBy(()-> loginService.login("colin", "123456"))
                .isInstanceOf(CartisanException.class)
                .hasMessage("用户名或密码不正确");
    }

    @Test
    public void when_password_not_valid_then_throw_exception() {
        // given
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);

        // then
        assertThatThrownBy(()-> loginService.login("colin", "123456"))
                .isInstanceOf(CartisanException.class)
                .hasMessage("用户名或密码不正确");
    }
}