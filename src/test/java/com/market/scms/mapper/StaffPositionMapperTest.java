package com.market.scms.mapper;

import com.market.scms.entity.staff.StaffPosition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/10 21:09
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StaffPositionMapperTest {

    @Resource
    private StaffPositionMapper staffPositionMapper;
    
    @Test
    public void insert() {
        StaffPosition staffPosition = new StaffPosition();
        staffPosition.setStaffPositionId(1);
        staffPosition.setStaffPositionName("总经理");
        System.out.println(staffPositionMapper.insert(staffPosition));
    }
    
    @Test
    public void query() {
        System.out.println(staffPositionMapper.queryById(1).getStaffPositionName());
    }
    
    @Test
    public void update() {
        StaffPosition staffPosition = new StaffPosition();
        staffPosition.setStaffPositionId(1);
        staffPosition.setStaffPositionName("总经理");
        System.out.println(staffPositionMapper.update(staffPosition));
    }
    
    @Test
    public void delete() {
        System.out.println(staffPositionMapper.delete(1));
    }
    
}
