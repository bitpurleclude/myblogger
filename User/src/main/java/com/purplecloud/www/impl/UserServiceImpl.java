package com.purplecloud.www.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.purplecloud.www.bean.Role;
import com.purplecloud.www.bean.User;
import com.purplecloud.www.bean.UserIdToRoleId;
import com.purplecloud.www.mapper.UserMapper;
import com.purplecloud.www.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService , UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserIdToRoleIdServiceImpl userIdToRoleIdService;
    @Autowired
    private HashMap<Integer, Role> roleIdHashMap;
    @Override
    public User getUserByUsername(String username) {
        QueryWrapper<User> query = new QueryWrapper(User.class);
        query.eq("email",username);
        User user = userMapper.selectOne(query);
        if (user!=null){
            getUserRoleByUser(user);
        }
        return user;
    }
    @Override
    public User getUserRoleByUser(User user){
        List<UserIdToRoleId> userIdToRoleIdByUserId = userIdToRoleIdService.getUserIdToRoleIdByUserId(user.getId());
        for (UserIdToRoleId userIdToRoleId : userIdToRoleIdByUserId) {
            Role role = roleIdHashMap.get(userIdToRoleId.getRoleId());
            user.getRolesHashMap().put(role.getRoleName(),role);
        }
        return user;
    }
    @Override
    public boolean checkHasResign(String email) {
         return getUserByUsername(email)!=null;
    }
    @Override
    public void insertUser(User user) {
       user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insert(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=getUserByUsername(username);
        if (user==null){
            try {
                throw UsernameNotFoundException.class.newInstance();
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        else return user;
    }
}
