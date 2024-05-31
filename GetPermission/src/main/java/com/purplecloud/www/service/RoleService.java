package com.purplecloud.www.service;

import com.purplecloud.www.bean.Role;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;

public interface RoleService {
    @Bean
    HashMap<String, Role> getAllRoleName();

    @Bean
    HashMap<Integer, Role> getAllRoleId();

    void getRolePermission(Role role);
}
