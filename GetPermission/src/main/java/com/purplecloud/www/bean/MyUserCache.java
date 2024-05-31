package com.purplecloud.www.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserCache implements UserCache {
    @Autowired
    private RedisTemplate<String,UserDetails> redisTemplate;
    @Override
    public UserDetails getUserFromCache(String username) {
        return redisTemplate.opsForValue().get(username);
    }

    @Override
    public void putUserInCache(UserDetails user) {
        redisTemplate.opsForValue().set(user.getUsername(),user);
    }

    @Override
    public void removeUserFromCache(String username) {
        redisTemplate.delete(username);
    }
}
