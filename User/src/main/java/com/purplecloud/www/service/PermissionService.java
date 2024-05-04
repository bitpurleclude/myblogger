package com.purplecloud.www.service;


import com.purplecloud.www.bean.Permission;

import java.util.HashMap;

public interface PermissionService {
    HashMap<Integer, Permission> getAllPermission();
}
