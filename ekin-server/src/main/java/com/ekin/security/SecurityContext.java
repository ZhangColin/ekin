package com.ekin.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityContext {
    private SecurityContext() {
    }

    public static CurrentUserInfo getCurrentUser() {
        return (CurrentUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
