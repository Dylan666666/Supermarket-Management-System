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
 * @Date: 2020/10/8 21:57
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SupermarketStaffServiceTest {

    //当前时间
    LocalDateTime now = LocalDateTime.now();
    
    @Resource
    private SupermarketStaffService supermarketStaffService;
    
    @Test
    public void insertTest() {
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
        System.out.println(supermarketStaffService.insertStaff(staff));
    }
    
    @Test
    public void queryTest() {
        System.out.println(supermarketStaffService.queryStaffByPhone("666").getStaffName());
        System.out.println(supermarketStaffService.staffLogin("666", "666").getStaffName());
    }
    
    @Test
    public void updateTest() {
        SupermarketStaff staff = supermarketStaffService.queryStaffByPhone("666");
        staff.setToken("666");
        System.out.println(supermarketStaffService.updateStaff(staff));
    }

    @Test
    public void findTokenTest() {
        System.out.println(supermarketStaffService.findByToken("666").getStaffName());
    }
    
    @Test
    public void logoutTest() {
        supermarketStaffService.logout("666");
    }
    
    @Test
    public void queryByCondition() {
        SupermarketStaff staff = new SupermarketStaff();
        staff.setStaffName("6");
        List<SupermarketStaff> staffList = supermarketStaffService.queryStaffByCondition(staff, 0, 100);
        for (SupermarketStaff supermarketStaff : staffList) {
            System.out.println(supermarketStaff.getStaffName());
        }
    }
    
}
