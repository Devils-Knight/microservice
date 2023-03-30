package com.autmaple.utils;

import org.springframework.util.Assert;

public class UserContextHolder {
    private static final ThreadLocal<UserContext> userContextHolder = new ThreadLocal<>();

    public static UserContext getUserContext() {
        UserContext userContext = userContextHolder.get();
        if (null == userContext) {
            userContext = createUserContext();
            userContextHolder.set(userContext);
        }
        return userContextHolder.get();
    }

    public static void setUserContext(UserContext userContext) {
        Assert.notNull(userContext, "Only non-null UserContext instance are permitted.");
        userContextHolder.set(userContext);
    }

    private static UserContext createUserContext() {
        return new UserContext();
    }
}
