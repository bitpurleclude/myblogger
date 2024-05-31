package com.purplecloud.useroperation.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.purplecloud.useroperation.bean.Relation;
import com.purplecloud.useroperation.mapper.RelationMapper;
import com.purplecloud.useroperation.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelationServiceImpl implements RelationService {
    @Autowired
    private RelationMapper relationMapper;

    @Override
    public void addRelation(Relation relation) {
        relationMapper.insert(relation);
    }

    @Override
    public void deleteRelation(Relation relation) {
        relation.setIsDeleted(true);
        relationMapper.updateById(relation);
    }

    @Override
    public List<Relation> getRelation(Relation relation) {
        List<Relation> relations = relationMapper.selectList(new QueryWrapper<Relation>()
                .eq("user_id", relation.getUserId())
                .eq("relation_id", relation.getRelationId())
                .eq("relation_type", relation.getRelationType())
                .eq("is_deleted", false));
        return relations;
    }

    @Override
    public List<Relation> getRelationByUserId(int userId) {
        List<Relation> relations = relationMapper.selectList(new QueryWrapper<Relation>()
                .eq("user_id", userId)
                .eq("is_deleted", false));
        return relations;
    }

}
