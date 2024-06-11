package com.purplecloud.userinfro.service;

import com.purplecloud.userinfro.bean.UserInform;

public interface UserService {
    UserInform getUserById(int id);

    void hartBeat(int id);

    boolean isOnline(int id);

    void insertUserInform(UserInform userInform);
}
