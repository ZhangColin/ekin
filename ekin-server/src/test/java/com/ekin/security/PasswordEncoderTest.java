package com.ekin.security;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

public class PasswordEncoderTest {
    @Test
    public void encodePasswordByBCryptPasswordEncoder() {
        final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        System.out.println(bCryptPasswordEncoder.encode("123456"));
    }

    @Test
    public void matchesByBCryptPasswordEncoder() {
        // given
        final String encodePassword1 = "$2a$10$tRC2HUxi/tl5XK4gw/mY1egn43ZH6sqBRU49JrH0vAyA3mEwnPXlG";
        final String encodePassword2 = "$2a$10$1OUc.r.DmlMKTPiXwhhWw.Sr0McOHHLOCNd6/Wgia7oeHgRjCOYeq";
        final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        // when
        final boolean result1 = bCryptPasswordEncoder.matches("123456", encodePassword1);
        final boolean result2 = bCryptPasswordEncoder.matches("123456", encodePassword2);

        // then
        assertThat(result1).isTrue();
        assertThat(result2).isTrue();
    }
}
