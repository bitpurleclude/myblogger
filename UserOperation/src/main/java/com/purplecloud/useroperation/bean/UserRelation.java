package com.purplecloud.useroperation.bean;

import java.util.List;

public class UserRelation {
    private int userId;
    private List<Relation> relations;

    public UserRelation() {
    }

    public UserRelation(int userId, List<Relation> relations) {
        this.userId = userId;
        this.relations = relations;
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
     * @return relations
     */
    public List<Relation> getRelations() {
        return relations;
    }

    /**
     * 设置
     * @param relations
     */
    public void setRelations(List<Relation> relations) {
        this.relations = relations;
    }

    public String toString() {
        return "UserRelation{userId = " + userId + ", relations = " + relations + "}";
    }
}
