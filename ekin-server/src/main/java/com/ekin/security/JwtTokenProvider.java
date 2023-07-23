package com.ekin.security;

import com.ekin.security.config.SecurityProperties;
import com.google.common.base.Strings;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

/**
 * @author colin
 */
@Component
@Slf4j
public class JwtTokenProvider implements InitializingBean {
    private final SecurityProperties securityProperties;
    private Key key;

    public JwtTokenProvider(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.key = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(
                        Encoders.BASE64.encode(
                                Strings.padEnd(securityProperties.getSecret(), 64, ' ').getBytes()
                        )));
    }

    /**
     * 根据用户名生成 token
     * @param userName
     * @return
     */
    public String generateToken(String userName) {
        return Jwts.builder()
                .setSubject(userName)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(generateExpirationDate())
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * 从 token 中获取登录用户名
     * @param token
     * @return
     */
    public String getUsername(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean verify(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
        }
        return false;
    }

    private Date generateExpirationDate() {
        return Date.from(Instant.now().plusSeconds(securityProperties.getExpiration()));
    }


}
