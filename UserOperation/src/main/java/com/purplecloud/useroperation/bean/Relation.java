package com.purplecloud.useroperation.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class Relation {
    @TableId(type=IdType.AUTO)
    private int id;
    private int userId;
    private RelationType relationType;
    private int relationId;
    private Boolean isDeleted =false;


    public Relation() {
    }

    public Relation(int id, int userId, RelationType relationType, int relationId, Boolean isDeleted) {
        this.id = id;
        this.userId = userId;
        this.relationType = relationType;
        this.relationId = relationId;
        this.isDeleted = isDeleted;
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
     * @return relationType
     */
    public RelationType getRelationType() {
        return relationType;
    }

    /**
     * 设置
     * @param relationType
     */
    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    /**
     * 获取
     * @return relationId
     */
    public int getRelationId() {
        return relationId;
    }

    /**
     * 设置
     * @param relationId
     */
    public void setRelationId(int relationId) {
        this.relationId = relationId;
    }

    /**
     * 获取
     * @return isDeleted
     */
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    /**
     * 设置
     * @param isDeleted
     */
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String toString() {
        return "Relation{id = " + id + ", userId = " + userId + ", relationType = " + relationType + ", relationId = " + relationId + ", isDeleted = " + isDeleted + "}";
    }
}
