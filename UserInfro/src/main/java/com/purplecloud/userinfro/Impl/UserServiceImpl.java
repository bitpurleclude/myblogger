package com.purplecloud.userinfro.Impl;

import com.purplecloud.userinfro.bean.UserInform;
import com.purplecloud.userinfro.mapper.UserMapper;
import com.purplecloud.userinfro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RedisTemplate<String, Boolean> onlineRedisTemplate;
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserInform getUserById(int id) {
        return userMapper.selectById(id);
    }
    @Override
    public void hartBeat(int id) {
        String key ="online:" + id;
        onlineRedisTemplate.opsForValue().set(key, true);
        onlineRedisTemplate.expire(key, 10, TimeUnit.SECONDS);
    }
    @Override
    public boolean isOnline(int id) {
        String key = "online:" + id;
        return Boolean.TRUE.equals(onlineRedisTemplate.hasKey(key));
    }
    @Override
    public void insertUserInform(UserInform userInform) {
        userMapper.insert(userInform);
    }

}
