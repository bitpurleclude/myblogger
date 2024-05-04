package com.purplecloud.www.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class User implements UserDetails, Serializable {
    @TableId(type = IdType.AUTO)
    private int id;
    private String password;
    private String email;
    @TableField(exist = false)
    private HashMap<String,Role> RolesHashMap = new HashMap<>();

    public User() {
    }

    public User(int id, String password, String email, HashMap<String, Role> RolesHashMap) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.RolesHashMap = RolesHashMap;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
            List<GrantedAuthority> authorities = new ArrayList<>();
            // 添加角色
            for (Role role : RolesHashMap.values()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));

                // 添加权限
                for (Permission permission : role.getPermissions()) {
                    authorities.add(new SimpleGrantedAuthority(permission.getPermission()));
                }
            }

            return authorities;
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * 获取
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * 设置
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 获取
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取
     * @return RolesHashMap
     */
    public HashMap<String, Role> getRolesHashMap() {
        return RolesHashMap;
    }

    /**
     * 设置
     * @param RolesHashMap
     */
    public void setRolesHashMap(HashMap<String, Role> RolesHashMap) {
        this.RolesHashMap = RolesHashMap;
    }

    public String toString() {
        return "User{id = " + id + ", password = " + password + ", email = " + email + ", RolesHashMap = " + RolesHashMap + "}";
    }

    public void setRole(Role role) {
        this.RolesHashMap.put(role.getRoleName(),role);
    }
}
