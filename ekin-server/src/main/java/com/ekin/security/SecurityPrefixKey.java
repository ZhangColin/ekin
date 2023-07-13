package com.ekin.security;

import com.cartisan.constant.BasePrefix;

public class SecurityPrefixKey extends BasePrefix {
    public SecurityPrefixKey(String prefix) {
        super(prefix);
    }

    public SecurityPrefixKey(long expireMilliseconds, String prefix) {
        super(expireMilliseconds, prefix);
    }

    @Override
    protected String businessPrefix() {
        return "security";
    }
}
