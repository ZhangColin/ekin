package com.ekin.security;

import com.ekin.system.user.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CurrentUserInfo implements UserDetails {

//    private final Long userId;
//    private final String username;
//    private final String password;
//    private final boolean isEnabled;
    private final User user;
    private final List<String> authorities;

    public CurrentUserInfo(User user, List<String> authorities) {
//        this.userId = userId;
//        this.username = username;
//        this.password = password;
//        this.isEnabled = isEnabled;
        this.user = user;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

//    public Long getUserId() {
//        return userId;
//    }

    public User getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.user.getStatus().equals(1);
    }
}
