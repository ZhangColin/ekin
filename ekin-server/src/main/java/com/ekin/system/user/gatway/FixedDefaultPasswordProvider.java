package com.ekin.system.user.gatway;

import com.ekin.system.user.domain.DefaultPasswordProvider;
import org.springframework.stereotype.Service;

/**
 * @author colin
 */
@Service
public class FixedDefaultPasswordProvider implements DefaultPasswordProvider {
    @Override
    public String generate() {
        return "123456";
    }
}
