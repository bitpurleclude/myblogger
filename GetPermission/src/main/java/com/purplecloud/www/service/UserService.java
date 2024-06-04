package com.purplecloud.www.service;


import com.purplecloud.www.bean.User;

public interface UserService {
    User login(String username, String password);

    User getUserByUsername(String username);

    User getUserRoleByUser(User user);

    boolean checkHasResign(String email);

    void insertUser(User user);
}
