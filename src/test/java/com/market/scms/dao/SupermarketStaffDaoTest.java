package com.market.scms.dao;

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
 * @Date: 2020/10/8 20:35
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SupermarketStaffDaoTest {
    @Resource
    private SupermarketStaffDao supermarketStaffDao;

    //当前时间
    LocalDateTime now = LocalDateTime.now();
    
    @Test
    public void InsertTest() {
        SupermarketStaff staff = new SupermarketStaff();
        staff.setToken("asdasd");
        staff.setStaffPhone("666");
        staff.setStaffPassword("666");
        staff.setStaffName("666");
        staff.setExpireTime(now);
        staff.setLoginTime(now);
        staff.setStaffStatus(1001);
        staff.setCreateTime(new Date());
        staff.setLastEditTime(new Date());
        System.out.println(supermarketStaffDao.insertStaff(staff));
    }
    
    @Test
    public void queryTest() {
        System.out.println(supermarketStaffDao.queryStaffByPhone("666").getStaffPhone());
        System.out.println(supermarketStaffDao.staffLogin("666", "666").getStaffPhone());
    }
    
    @Test
    public void updateTest() {
        System.out.println(supermarketStaffDao.findByToken("666").getStaffPhone());
        SupermarketStaff staff = supermarketStaffDao.findByToken("asdasd");
        staff.setToken("666");
        System.out.println(supermarketStaffDao.updateStaff(staff));
    }
    
    @Test
    public void queryByConditionTest() {
        SupermarketStaff staff = new SupermarketStaff();
        staff.setStaffName("66666");
        List<SupermarketStaff> staffList = supermarketStaffDao.queryStaffByCondition(staff, 0, 100);
        for (SupermarketStaff supermarketStaff : staffList) {
            System.out.println(supermarketStaff.getStaffName());
        }
    }
    
}
