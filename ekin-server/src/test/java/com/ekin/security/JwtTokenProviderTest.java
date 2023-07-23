package com.ekin.security;

import com.ekin.security.config.SecurityProperties;
import com.google.common.base.Strings;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JwtTokenProviderTest {
    @Ignore
    @Test
    public void test_secret_encode_and_decode() {
        String secret = "Cartisan";

        final byte[] originBytes = Strings.padEnd(secret, 64, ' ').getBytes();
        System.out.println("originBytes length: " + originBytes.length);

        final String encode = Encoders.BASE64.encode(originBytes);
        System.out.println("encode: " + encode);

        final byte[] decodeBytes = Decoders.BASE64.decode(encode);
        System.out.println("decodeBytes length: " + decodeBytes.length);
    }

    @Test
    public void should_be_generate_token() throws Exception {
        // given
        final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(new SecurityProperties());
        jwtTokenProvider.afterPropertiesSet();

        // when
        final String token = jwtTokenProvider.generateToken("colin");

        // then
        assertThat(token).isNotNull();
        System.out.println(token);
    }

    @Test
    public void should_be_get_username_from_token() throws Exception {
        // given
        final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(new SecurityProperties());
        jwtTokenProvider.afterPropertiesSet();

        final String token = jwtTokenProvider.generateToken("colin");

        // when
        final String userName = jwtTokenProvider.getUsername(token);

        // then
        assertThat(userName).isEqualTo("colin");
    }

    @Test
    public void should_when_expired_then_verify_fail() throws Exception {
        // given
        final SecurityProperties securityProperties = new SecurityProperties();
        securityProperties.setExpiration(-1L);
        final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(securityProperties);
        jwtTokenProvider.afterPropertiesSet();

        final String token = jwtTokenProvider.generateToken("colin");

        // when
        final boolean result = jwtTokenProvider.verify(token);

        // then
        assertThat(result).isFalse();
    }
}

