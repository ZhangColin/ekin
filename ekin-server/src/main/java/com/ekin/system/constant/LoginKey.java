package com.ekin.system.constant;

import com.cartisan.constants.BasePrefix;

/**
 * @author colin
 */
public class LoginKey extends BasePrefix {
    private LoginKey(String prefix) {
        super(prefix);
    }

    private LoginKey(long expireMilliseconds, String prefix) {
        super(expireMilliseconds, prefix);
    }

    @Override
    protected String businessPrefix() {
        return "Login";
    }

    public static LoginKey token = new LoginKey(1000 * 60 * 60 * 2, "tk");
}
