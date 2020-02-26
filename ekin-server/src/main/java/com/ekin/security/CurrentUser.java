package com.ekin.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUser {
    private CurrentUser() {
    }

    private static CurrentUserInfo getCurrentUser() {
        return (CurrentUserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Long getUserId() {
        return getCurrentUser().getUserId();
    }

    public static String getUsername() {
        return getCurrentUser().getUsername();
    }
}
