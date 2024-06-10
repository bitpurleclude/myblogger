package com.purplecloud.www.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.purplecloud.www.bean.Role;
import com.purplecloud.www.bean.User;
import com.purplecloud.www.bean.UserIdToRoleId;
import com.purplecloud.www.feign.UserInformFeign;
import com.purplecloud.www.mapper.UserMapper;
import com.purplecloud.www.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@Lazy
public class UserServiceImpl implements UserService , UserDetailsService {
    @Autowired
    UserInformFeign userInformFeign;
    @Autowired
    UserMapper userMapper;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserIdToRoleIdServiceImpl userIdToRoleIdService;
    @Autowired
    private HashMap<Integer, Role> roleIdHashMap;
    @Override
    public User register(User user) {
        if (getUserByUsername(user.getEmail())!=null){
            return null;
        }
        user= insertUser(user);
        userIdToRoleIdService.insertUserIdToRoleId(user.getId(),1);
        userInformFeign.insertUserInform(user.getId(),user.getEmail());
        return user;
    }
    
    @Override
    public User login(String username, String password) {
        User user = getUserByUsername(username);
        if (user!=null&&passwordEncoder.matches(password,user.getPassword())){
            return user;
        }
        return null;
    }
    public User initUser(User user){
        List<UserIdToRoleId> userIdToRoleIdByUserId = userIdToRoleIdService.getUserIdToRoleIdByUserId(user.getId());
        user.getRolesHashMap().clear();
        for (UserIdToRoleId userIdToRoleId : userIdToRoleIdByUserId) {
            Role role = roleIdHashMap.get(userIdToRoleId.getRoleId());
            user.getRolesHashMap().put(role.getRoleName(),role);
        }
        return getUserRoleByUser(user);
    }
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
    public User insertUser(User user) {
       user.setPassword(passwordEncoder.encode(user.getPassword()));
       userMapper.insert(user);
       return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
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
