package com.ekin.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author colin
 */
@Component
@ConfigurationProperties(prefix = "cartisan.security")
@Getter
@Setter
public class SecurityProperties {
    /**
     * Jwt 加密使用的密钥
     */
    private String secret = "cartisan";

    /**
     * Jwt 令牌过期时间(60*60*24)秒
     */
    private Long expiration = 604800L;

    /**
     * Jwt 存储的请求头
     */
    private String tokenHeader = "Authorization";

    /**
     * jwt 负载中的开头
     */
    private String tokenHead = "Bearer";

    private IgnoreUrlsProperties ignored;
}
