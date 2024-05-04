package com.purplecloud.www.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("role_id_permission_id")
public class RoleIdToPermissionId {
    @TableId(type = IdType.AUTO)
    int Id;
    int roleId;
    int permissionId;

    public RoleIdToPermissionId() {
    }

    public RoleIdToPermissionId(int Id, int roleId, int permissionId) {
        this.Id = Id;
        this.roleId = roleId;
        this.permissionId = permissionId;
    }

    /**
     * 获取
     * @return Id
     */
    public int getId() {
        return Id;
    }

    /**
     * 设置
     * @param Id
     */
    public void setId(int Id) {
        this.Id = Id;
    }

    /**
     * 获取
     * @return roleId
     */
    public int getRoleId() {
        return roleId;
    }

    /**
     * 设置
     * @param roleId
     */
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    /**
     * 获取
     * @return permissionId
     */
    public int getPermissionId() {
        return permissionId;
    }

    /**
     * 设置
     * @param permissionId
     */
    public void setPermissionId(int permissionId) {
        this.permissionId = permissionId;
    }

    public String toString() {
        return "RoleIdToPermissionId{Id = " + Id + ", roleId = " + roleId + ", permissionId = " + permissionId + "}";
    }
}
