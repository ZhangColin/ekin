package com.ekin.system.user.application;

import com.cartisan.exceptions.CartisanException;
import com.cartisan.security.CartisanUser;
import com.ekin.constant.SystemCodeMessage;
import com.ekin.system.user.UserRepository;
import com.ekin.system.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author colin
 */
@Service
public class CartisanUserDetailService implements UserDetailsService {
    private final UserRepository repository;


    @Autowired
    public CartisanUserDetailService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = requireUserPresent(repository.findByUsername(username));

        return buildCartisanUser(user);
    }

    private CartisanUser buildCartisanUser(User user) {
        return new CartisanUser(user.getUsername(), user.getPassword());
    }

    private User requireUserPresent(Optional<User> userOptional) {
        return userOptional
                .orElseThrow(() -> new CartisanException(SystemCodeMessage.ERROR_USERNAME_OR_PASSWORD));
    }


}
