package com.ekin.system.user;

import com.ekin.system.user.domain.User;

public class UserFixture {
    public static final Long USER_ID = 1L;
    public static final String USERNAME = "colin";
    public static final String PHONE = "13962830605";
    public static final String EMAIL = "stwyhm@126.com";
    public static final String REAL_NAME = "colin";
    public static final String PASSWORD = "123456";

    public static User userOf() {
        return new User(USER_ID, USERNAME, PHONE, EMAIL, PASSWORD, REAL_NAME);
    }
}
