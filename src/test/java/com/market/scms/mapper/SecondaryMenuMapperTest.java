package com.market.scms.mapper;

import com.market.scms.entity.staff.SecondaryMenu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/10 15:43
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SecondaryMenuMapperTest {
    
    @Resource
    private SecondaryMenuMapper secondaryMenuMapper;
    
    @Test
    public void insert() {
        SecondaryMenu secondaryMenu = new SecondaryMenu();
        secondaryMenu.setPrimaryMenuId(1);
        secondaryMenu.setSecondaryMenuName("增加");
        secondaryMenu.setSecondaryMenuUrl("/staff/insertStaff");
        secondaryMenu.setSecondaryMenuWeight(10);
        System.out.println(secondaryMenuMapper.insert(secondaryMenu));
    }
    
    @Test
    public void query() {
        System.out.println(secondaryMenuMapper.queryById(1).getSecondaryMenuName());
    }
    
    @Test
    public void update() {
        SecondaryMenu secondaryMenu = new SecondaryMenu();
        secondaryMenu.setSecondaryMenuId(1);
        secondaryMenu.setPrimaryMenuId(1);
        secondaryMenu.setSecondaryMenuName("增加");
        secondaryMenu.setSecondaryMenuUrl("/staff/insertStaff");
        secondaryMenu.setSecondaryMenuWeight(10);
        System.out.println(secondaryMenuMapper.update(secondaryMenu));
    }
    
    @Test
    public void delete() {
        System.out.println(secondaryMenuMapper.delete(1));
    }
    
    
}
