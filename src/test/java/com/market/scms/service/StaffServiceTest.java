package com.market.scms.service;

import com.market.scms.entity.SupermarketStaff;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/2 14:58
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StaffServiceTest {
    
    @Resource
    private StaffService staffService;

    //当前时间
    LocalDateTime now = LocalDateTime.now();
    
    @Test
    public void InsertTest() {
        SupermarketStaff staff = new SupermarketStaff();
        staff.setToken("asdasd");
        staff.setStaffPhone("777");
        staff.setStaffPassword("777");
        staff.setStaffName("777");
        staff.setExpireTime(now);
        staff.setLoginTime(now);
        staff.setStaffStatus(1001);
        staff.setCreateTime(new Date());
        staff.setLastEditTime(new Date());
        System.out.println(staffService.insertStaff(staff));
    }

    @Test
    public void updateStaffPosition() {
        SupermarketStaff staff = staffService.queryStaffByPhone("333");
        staff.setStaffPosition(4);
        System.out.println(staffService.updateStaffPosition(staff));
    }

    @Test
    public void queryTest() {
        System.out.println(staffService.queryStaffByPhone("123").getStaffPhone());
   //     System.out.println(staffService.staffLogin("666", "666").getStaffPhone());
    }

    @Test
    public void queryByConditionTest() {
        SupermarketStaff staff = new SupermarketStaff();
        staff.setStaffName("666");
        List<SupermarketStaff> staffList = staffService.queryStaffByCondition(staff, 0, 100);
        for (SupermarketStaff supermarketStaff : staffList) {
            System.out.println(supermarketStaff.getStaffName());
        }
    }
    
}
