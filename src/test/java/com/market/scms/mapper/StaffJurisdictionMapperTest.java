package com.market.scms.mapper;

import com.market.scms.entity.staff.StaffJurisdiction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/10 18:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StaffJurisdictionMapperTest {
    
    @Resource
    private StaffJurisdictionMapper mapper;
    
    @Test
    public void insert() {
        StaffJurisdiction staffJurisdiction = new StaffJurisdiction();
        staffJurisdiction.setFunctionId(1);
        staffJurisdiction.setJurisdictionStatus(1);
        staffJurisdiction.setStaffId(1);
        System.out.println(mapper.insert(staffJurisdiction));
    }
    
    @Test
    public void query() {
        System.out.println(mapper.queryById(1).get(0).getJurisdictionStatus());
    }
    
    @Test
    public void update() {
        StaffJurisdiction staffJurisdiction = new StaffJurisdiction();
        staffJurisdiction.setFunctionId(1);
        staffJurisdiction.setJurisdictionStatus(1);
        staffJurisdiction.setStaffId(1);
        System.out.println(mapper.update(staffJurisdiction));
    }
    
}
