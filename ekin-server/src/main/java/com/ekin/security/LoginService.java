package com.ekin.security;

import com.cartisan.constant.CodeMessage;
import com.cartisan.exception.CartisanException;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author colin
 */
@Component
public class LoginService {
    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final HashOperations<String, String, Object> hashOperations;

    public static final CodeMessage LOGIN_ERROR = CodeMessage.FAIL.fillArgs("用户名或密码不正确");

    public LoginService(UserDetailsService userDetailsService, JwtTokenProvider tokenProvider,
                        PasswordEncoder passwordEncoder, HashOperations<String, String, Object> hashOperations) {
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.hashOperations = hashOperations;
    }

    public String login(String username, String password){
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails==null ||
                !passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new CartisanException(LOGIN_ERROR);
        }

        authenticate(userDetails);

        final String token = tokenProvider.generateToken(userDetails.getUsername());

        hashOperations.put("onlineUsers", userDetails.getUsername(), System.currentTimeMillis());

        return token;
    }

    public void logout(String token) {
        final String username = tokenProvider.getUsername(token);
        hashOperations.delete("onlineUsers", username);
    }

    public void logoutByUsername(String username) {
        hashOperations.delete("onlineUsers", username);
    }

    private void authenticate(UserDetails userDetails) {
        final UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
