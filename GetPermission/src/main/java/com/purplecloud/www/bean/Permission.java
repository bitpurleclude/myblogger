package com.purplecloud.www.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("permission")
public class Permission {
    @TableId(type = IdType.AUTO)
    int id;
    String permission;

    public Permission() {
    }

    public Permission(int id, String permission) {
        this.id = id;
        this.permission = permission;
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
     * @return permissionName
     */
    public String getPermission() {
        return permission;
    }

    /**
     * 设置
     * @param permission
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String toString() {
        return "Permission{id = " + id + ", permissionName = " + permission + "}";
    }
}
