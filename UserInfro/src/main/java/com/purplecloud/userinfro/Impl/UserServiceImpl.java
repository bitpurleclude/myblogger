package com.purplecloud.userinfro.Impl;

import com.purplecloud.userinfro.bean.UserInform;
import com.purplecloud.userinfro.mapper.UserMapper;
import com.purplecloud.userinfro.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserInform getUserById(int id) {
        return userMapper.selectById(id);
    }
    @Override
    public void insertUserInform(UserInform userInform) {
        userMapper.insert(userInform);
    }

}
