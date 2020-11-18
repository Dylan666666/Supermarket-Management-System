package com.market.scms.mapper;

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
public class SupermarketStaffMapperTest {
    @Resource
    private SupermarketStaffMapper supermarketStaffMapper;

    //当前时间
    LocalDateTime now = LocalDateTime.now();
    
    @Test
    public void InsertTest() {
        SupermarketStaff staff = new SupermarketStaff();
        staff.setToken("asdasd");
        staff.setStaffPhone("66666");
        staff.setStaffPassword("66666");
        staff.setStaffName("66666");
        staff.setExpireTime(now);
        staff.setLoginTime(now);
        staff.setStaffStatus(1001);
        staff.setCreateTime(new Date());
        staff.setLastEditTime(new Date());
        System.out.println(supermarketStaffMapper.insertStaff(staff));
    }

    @Test
    public void updateTest() {
        SupermarketStaff staff = supermarketStaffMapper.queryStaffByPhone("222");
        staff.setStaffPosition(4);
        System.out.println(supermarketStaffMapper.updateStaffPosition(staff));
    }
    
    @Test
    public void queryTest() {
        System.out.println(supermarketStaffMapper.queryStaffByPhone("666").getStaffPhone());
        System.out.println(supermarketStaffMapper.staffLogin("666", "666").getStaffPhone());
    }
    
    @Test
    public void queryByConditionTest() {
        SupermarketStaff staff = new SupermarketStaff();
        staff.setStaffName("666");
        List<SupermarketStaff> staffList = supermarketStaffMapper.queryStaffByCondition(staff, 0, 100);
        for (SupermarketStaff supermarketStaff : staffList) {
            System.out.println(supermarketStaff.getStaffName());
        }
    }
    
}
