package com.ekin.security;

import com.cartisan.constant.CodeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.cartisan.response.ResponseUtil.fail;

/**
 * @author colin
 */
@RestControllerAdvice
@Slf4j
@Order(0)
public class SecurityExceptionHandler {
    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<?> error(AuthenticationException authenticationException) {
        // 当用户尝试访问安全的 REST 资源而不提供任何凭据时，将调用此方法发送 401 响应
        log.warn("没有身份验证：", authenticationException);
        return fail(CodeMessage.UNAUTHORIZED);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<?> error(AccessDeniedException accessDeniedException) {
        // 当用户在没有授权的情况下访问受保护的 REST 资源时，将调用此方法发送 403 响应
        log.warn("没有获得授权：", accessDeniedException);
        return fail(CodeMessage.FORBIDDEN);
    }
}

