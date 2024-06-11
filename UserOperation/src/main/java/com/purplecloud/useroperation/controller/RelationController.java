package com.purplecloud.useroperation.controller;

import com.purplecloud.useroperation.bean.Relation;
import com.purplecloud.useroperation.bean.RelationType;
import com.purplecloud.useroperation.impl.RelationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/relation")
public class RelationController {
@Autowired
private RelationServiceImpl relationService;

    @RequestMapping("/request_Relation")
    public void addRelation(@RequestBody Relation relation) {
        relationService.relationRequest(relation);
    }
    @RequestMapping("/deleteRelation")
    public void deleteRelation(@RequestBody Relation relation) {
        Relation relation1 = new Relation();
        relation1.setUserId(relation.getRelationId());
        relation1.setRelationId(relation.getUserId());
        relation1.setRelationType(relation.getRelationType());
        relationService.deleteRelation(relation1);
        relationService.deleteRelation(relation);
    }
    @RequestMapping("/newGroup")
    public void addGroup(@RequestBody Relation relation) {
        System.out.println("Add group");
    }
    @RequestMapping("/getRelation")
    public List<Relation> getRelation(@RequestParam int userId) {
        Relation relation = new Relation();
        relation.setUserId(userId);
        return relationService.getRelation(relation);
    }
    @RequestMapping("/getRequestRelation")
    public List<Relation> getRequestRelation(@RequestParam int userId) {
        Relation relation = new Relation();
        relation.setRelationId(userId);
        relation.setRelationType(RelationType.PRIVATE);
        return relationService.getRelationFromRedis(relation);
    }
    @RequestMapping("/unconfirmedTheRelationship")
    public void unconfirmedTheRelationship(@RequestBody Relation relation) {
        relationService.deleteRelationFormRedis(relation);
    }
    @RequestMapping("/confirmTheRelationship")
    public void confirmRelation(@RequestBody Relation relation) {
        Relation relation2 = new Relation();
        relation2.setUserId(relation.getRelationId());
        relation2.setRelationId(relation.getUserId());
        relation2.setRelationType(relation.getRelationType());
        List<Relation> relationFromRedis = relationService.getRelationFromRedis(relation2);
        if (relationFromRedis.size() == 1) {
            Relation relation1 = new Relation();
            relation1.setUserId(relation.getRelationId());
            relation1.setRelationId(relation.getUserId());
            relation1.setRelationType(relation.getRelationType());
            relationService.addRelation(relation1);
            relationService.addRelation(relation);
            relationService.deleteRelationFormRedis(relation);
            relationService.deleteRelationFormRedis(relation1);
        }else {
            throw new RuntimeException("Relation is not exist or more than one");
        }
        System.out.println("Confirm relation");
    }
}
