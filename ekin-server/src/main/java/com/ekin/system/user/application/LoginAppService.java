package com.ekin.system.user.application;

import com.cartisan.exceptions.CartisanException;
import com.cartisan.security.CartisanUser;
import com.cartisan.security.JwtTokenProvider;
import com.ekin.constant.SystemCodeMessage;
import com.ekin.system.user.UserRepository;
import com.ekin.system.user.domain.User;
import com.ekin.system.user.request.LoginCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author colin
 */
@Service
public class LoginAppService{
    private final UserRepository repository;
    private final JwtTokenProvider tokenProvider;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public LoginAppService(UserRepository repository, JwtTokenProvider tokenProvider, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.tokenProvider = tokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(LoginCommand loginCommand) {
        final User user = requireUserPresent(repository.findByUsername(loginCommand.getUsername()));

        if (!passwordEncoder.matches(loginCommand.getPassword(), user.getPassword())) {
            throw new CartisanException(SystemCodeMessage.ERROR_USERNAME_OR_PASSWORD);
        }

        securityLogin(user);

        return tokenProvider.generateToken(user.getUsername());

    }

    public void logout(String token) {
        // 清除 token 缓存

        // 清除角色缓存
    }

    private void securityLogin(User user) {
        final CartisanUser cartisanUser = buildCartisanUser(user);

        final UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(cartisanUser, null, cartisanUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private CartisanUser buildCartisanUser(User user) {
        return new CartisanUser(user.getUsername(), user.getPassword());
    }

    private User requireUserPresent(Optional<User> userOptional) {
        return userOptional
                .orElseThrow(() -> new CartisanException(SystemCodeMessage.ERROR_USERNAME_OR_PASSWORD));
    }


}
