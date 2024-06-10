package com.purplecloud.useroperation.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.purplecloud.useroperation.bean.Relation;
import com.purplecloud.useroperation.mapper.RelationMapper;
import com.purplecloud.useroperation.service.RelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RelationServiceImpl implements RelationService {
    @Autowired
    private RelationMapper relationMapper;
    @Autowired
    private RedisTemplate<String, Relation> relationRedisTemplate;

    public void relationRequest(Relation relation) {
        String key = relation.getRelationId() + ":" + relation.getRelationType();
        relationRedisTemplate.opsForList().rightPush(key, relation);
        relationRedisTemplate.expire(key, 24, TimeUnit.HOURS);
    }
    public void deleteRelationFormRedis(Relation relation) {
        String key = relation.getRelationId() + ":" + relation.getRelationType();
        relationRedisTemplate.delete(key);
    }
    public List<Relation> getRelationFromRedis(Relation relation) {
        String key = relation.getRelationId() + ":" + relation.getRelationType();
        List<Relation> relations = relationRedisTemplate.opsForList().range(key, 0, -1);
        return relations;
    }
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
