package com.ekin.system.user.gatway;

import com.ekin.system.user.domain.DefaultPasswordProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author colin
 */
@Service
public class FixedDefaultPasswordProvider implements DefaultPasswordProvider {
    private final PasswordEncoder passwordEncoder;

    public FixedDefaultPasswordProvider(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String geneate() {
        return passwordEncoder.encode("123456");
    }
}
