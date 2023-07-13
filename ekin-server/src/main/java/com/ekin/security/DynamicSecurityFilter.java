package com.ekin.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author colin
 */
@Component
@ConditionalOnBean(DynamicSecurityService.class)
public class DynamicSecurityFilter extends AbstractSecurityInterceptor implements Filter {
    private final DynamicSecurityMetadataSource dynamicSecurityMetadataSource;
    private final SecurityProperties securityProperties;

    public DynamicSecurityFilter(DynamicSecurityMetadataSource dynamicSecurityMetadataSource, SecurityProperties securityProperties) {
        this.dynamicSecurityMetadataSource = dynamicSecurityMetadataSource;
        this.securityProperties = securityProperties;
    }

    @Autowired
    public void setMyAccessDecisionManager(DynamicAccessDecisionManager dynamicAccessDecisionManager) {
        super.setAccessDecisionManager(dynamicAccessDecisionManager);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final FilterInvocation filterInvocation = new FilterInvocation(request, response, chain);
        final HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        // OPTIONS 请求直接放行
        if (httpServletRequest.getMethod().equals(HttpMethod.OPTIONS.toString())) {
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
            return;
        }

        // 白名单直接放行
        final AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (String path : securityProperties.getIgnored().getUrls()) {
            if (antPathMatcher.match(path, httpServletRequest.getRequestURI())) {
                filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
                return;
            }
        }

        // 此处会调用 AccessDecisionManager 中的 decide 方法进行鉴权操作
        final InterceptorStatusToken interceptorStatusToken = super.beforeInvocation(filterInvocation);
        try{
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        } finally {
            super.afterInvocation(interceptorStatusToken, null);
        }
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return dynamicSecurityMetadataSource;
    }
}
