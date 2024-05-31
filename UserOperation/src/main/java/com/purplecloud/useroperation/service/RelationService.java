package com.purplecloud.useroperation.service;

import com.purplecloud.useroperation.bean.Relation;
import com.purplecloud.useroperation.mapper.RelationMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface RelationService {
    void addRelation(Relation relation);

    void deleteRelation(Relation relation);

    List<Relation> getRelation(Relation relation);

    List<Relation> getRelationByUserId(int userId);
}
