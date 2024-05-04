package com.purplecloud.www.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("user_id_role_id")
public class UserIdToRoleId {
    @TableId(type = IdType.AUTO)
    private int id;
    private int userId;
    private int roleId;

    public UserIdToRoleId() {
    }

    public UserIdToRoleId(int id, int userId, int roleId) {
        this.id = id;
        this.userId = userId;
        this.roleId = roleId;
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
     * @return userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * 设置
     * @param userId
     */
    public void setUserId(int userId) {
        this.userId = userId;
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

    public String toString() {
        return "UserIdToRoleId{id = " + id + ", userId = " + userId + ", roleId = " + roleId + "}";
    }
}
