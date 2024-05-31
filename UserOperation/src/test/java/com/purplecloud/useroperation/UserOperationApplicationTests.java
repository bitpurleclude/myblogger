package com.purplecloud.useroperation;

import com.purplecloud.useroperation.bean.Relation;
import com.purplecloud.useroperation.bean.RelationType;
import com.purplecloud.useroperation.service.RelationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserOperationApplicationTests {

    @Test
    void contextLoads() {
    }
    @Autowired
    private RelationService relationService;
    @Test
    void testRelationService() {
        Relation relation = new Relation();
        relation.setRelationType(RelationType.PERSON);
        relation.setRelationId(2);
        relation.setUserId(1);
        relation.setIsDeleted(false);
        relationService.addRelation(relation);
    }
    @Test
    void testDeleteRelation() {
        Relation relation = new Relation();
        relation.setId(0);
        relationService.deleteRelation(relation);
    }
    @Test
    void testGetRelation() {
        System.out.println(relationService.getRelationByUserId(1));
    }
}
