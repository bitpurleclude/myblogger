package com.purplecloud.www.service;


import com.purplecloud.www.bean.User;

public interface UserService {
    User register(User user);

    User login(String username, String password);

    User getUserByUsername(String username);

    User getUserRoleByUser(User user);

    boolean checkHasResign(String email);

    User insertUser(User user);
}
