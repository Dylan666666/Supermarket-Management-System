package com.market.scms.mapper;

import com.market.scms.entity.staff.StaffPositionRelation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/10 23:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StaffPositionRelationMapperTest {
    
    @Resource
    private StaffPositionRelationMapper mapper;
    
    @Test
    public void insert() {
        StaffPositionRelation relation = new StaffPositionRelation();
        relation.setStaffId(26);
        relation.setStaffPositionId(4);
        relation.setStaffPositionStatus(1);
        System.out.println(mapper.insert(relation));
    }
    
    @Test
    public void query() {
        System.out.println(mapper.queryById(1).get(0).getStaffPositionStatus());
    }

    @Test
    public void update() {
        StaffPositionRelation relation = new StaffPositionRelation();
        relation.setStaffId(1);
        relation.setStaffPositionId(1);
        relation.setStaffPositionStatus(1);
        System.out.println(mapper.update(relation));
    }

    @Test
    public void delete() {
        StaffPositionRelation relation = new StaffPositionRelation();
        relation.setStaffId(1);
        relation.setStaffPositionId(1);
        relation.setStaffPositionStatus(1);
        System.out.println(mapper.delete(relation));
    }
    
}
