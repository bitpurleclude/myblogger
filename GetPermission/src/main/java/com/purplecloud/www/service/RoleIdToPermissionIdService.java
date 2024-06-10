package com.purplecloud.www.service;

import com.purplecloud.www.bean.RoleIdToPermissionId;

import java.util.List;

public interface RoleIdToPermissionIdService {
    List<RoleIdToPermissionId> getRoleIdToPermissionIdByRoleId(int roleId);

    void insertRoleIdToPermissionId(int roleId, int permissionId);
}
