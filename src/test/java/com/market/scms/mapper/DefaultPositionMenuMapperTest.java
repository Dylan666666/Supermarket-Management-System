package com.market.scms.mapper;

import com.market.scms.entity.staff.DefaultPositionMenu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/9 22:36
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DefaultPositionMenuMapperTest {
    
    @Resource
    private DefaultPositionMenuMapper defaultPositionMenuMapper;
    
    @Test
    public void insert() {
        DefaultPositionMenu menu = new DefaultPositionMenu();
        menu.setSecondaryMenuId(1);
        menu.setStaffPositionId(1);
        System.out.println(defaultPositionMenuMapper.insert(menu));
    }
    
    @Test
    public void query() {
        System.out.println(defaultPositionMenuMapper.queryAll().get(0).getSecondaryMenuId());
        System.out.println(defaultPositionMenuMapper.queryByPositionId(1).get(0).getSecondaryMenuId());
    }
    
    @Test
    public void delete() {
        DefaultPositionMenu menu = new DefaultPositionMenu();
        menu.setSecondaryMenuId(1);
        menu.setStaffPositionId(1);
        System.out.println(defaultPositionMenuMapper.delete(menu));
    }
    
}
