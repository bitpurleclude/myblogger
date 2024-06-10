package com.purplecloud.www.service;

import com.purplecloud.www.bean.UserIdToRoleId;

import java.util.List;

public interface UserIdToRoleIdService{
    List<UserIdToRoleId> getUserIdToRoleIdByUserId(int userId);

    void insertUserIdToRoleId(int userId, int roleId);
}
