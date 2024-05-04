package com.purplecloud.www.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.ArrayList;
import java.util.List;
@TableName("role")
public class Role {
    @TableField("id")
    private int id;
    @TableField("role_name")
    private String roleName;
    @TableField(exist = false)
    private List<Permission> permissions =new ArrayList<>();

    public Role() {
    }

    public Role(int id, String roleName) {
        this.id = id;
        this.roleName = roleName;
    }

    public Role(int id, String roleName, List<Permission> permissions) {
        this.id = id;
        this.roleName = roleName;
        this.permissions = permissions;
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
     * @return roleName
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 设置
     * @param roleName
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String toString() {
        return "Role{id = " + id + ", roleName = " + roleName + "}";
    }

    /**
     * 获取
     * @return permissions
     */
    public List<Permission> getPermissions() {
        return permissions;
    }

    /**
     * 设置
     * @param permissions
     */
    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
