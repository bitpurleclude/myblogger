package com.purplecloud.gateway.util;

public class LoginUserInfoHelper {
    private static final ThreadLocal<Long> userId = new ThreadLocal<>();
    private static final ThreadLocal<String> email = new ThreadLocal<>();

    public static void setUserId(Long id) {
        userId.set(id);
    }

    public static Long getUserId() {
        return userId.get();
    }

    public static void setEmail(String userEmail) {
        email.set(userEmail);
    }

    public static String getEmail() {
        return email.get();
    }

    public static void clear() {
        // 清除当前线程存储的用户信息
        userId.remove();
        email.remove();
    }
}
