package com.purplecloud.useroperation.service;

import com.purplecloud.useroperation.bean.Relation;

import java.util.List;

public interface RelationService {
    void addRelation(Relation relation);

    void deleteRelation(Relation relation);

    List<Relation> getRelation(Relation relation);

    List<Relation> getRelationByUserId(int userId);
}
