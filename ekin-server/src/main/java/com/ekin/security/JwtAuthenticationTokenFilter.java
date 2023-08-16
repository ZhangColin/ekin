package com.ekin.security;

import com.ekin.security.config.SecurityProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

/**
 * JWT 登录授权过滤器
 *
 * @author colin
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider tokenProvider;
    private final HashOperations<String, String, Object> hashOperations;
    private final SecurityProperties securityProperties;

    public JwtAuthenticationTokenFilter(UserDetailsService userDetailsService, JwtTokenProvider tokenProvider,
                                        HashOperations<String, String, Object> hashOperations,
                                        SecurityProperties securityProperties) {
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
        this.hashOperations = hashOperations;
        this.securityProperties = securityProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String token = getToken(request);
        if (Strings.isNotBlank(token) &&
                tokenProvider.verify(token) &&
                validateOnlineUser(tokenProvider.getUsername(token)) &&
                SecurityContextHolder.getContext().getAuthentication() == null) {
            final String userName = tokenProvider.getUsername(token);
            log.info("checking username:[{}]", userName);

            final UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);

            if (userName.equals(userDetails.getUsername())) {
                final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                log.info("authenticated user:[{}]", userName);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(securityProperties.getTokenHeader()))
                .filter(authHeader -> Strings.isNotBlank(authHeader)
                        && authHeader.startsWith(securityProperties.getTokenHead()))
                .map(authHeader -> authHeader.substring(securityProperties.getTokenHead().length()).trim())
                .orElse(null);
    }

    private boolean validateOnlineUser(String userName){
        if(hashOperations.hasKey("onlineUsers", userName)){
            final long timeValue = Long.parseLong(Objects.requireNonNull(hashOperations.get("onlineUsers", userName)).toString());
            if (Instant.ofEpochMilli(timeValue).plus(120, ChronoUnit.MINUTES).isBefore(Instant.now())) {
                hashOperations.delete("onlineUsers", userName);
                return false;
            }
            else{
                if (Instant.ofEpochMilli(timeValue).plus(5, ChronoUnit.MINUTES).isBefore(Instant.now())) {
                    hashOperations.put("onlineUsers", userName, System.currentTimeMillis());
                }

                return true;
            }
        }
        return false;
    }
}
