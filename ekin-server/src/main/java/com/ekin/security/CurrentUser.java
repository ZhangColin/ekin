package com.ekin.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CurrentUser {
    private CurrentUserInfo getCurrentUser() {
        return (CurrentUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Long getUserId() {
        return getCurrentUser().getUserId();
    }

    public String getUsername() {
        return getCurrentUser().getUsername();
    }
}
