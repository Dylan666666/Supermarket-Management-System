package com.market.scms.mapper;

import com.market.scms.entity.staff.PrimaryMenu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/10 10:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PrimaryMenuMapperTest {
    
    @Resource
    private PrimaryMenuMapper menuMapper;
    
    @Test
    public void insert() {
        PrimaryMenu primaryMenu = new PrimaryMenu();
        primaryMenu.setPrimaryMenuId(1);
        primaryMenu.setPrimaryMenuName("库房管理");
        primaryMenu.setPrimaryMenuWeight(10);
        System.out.println(menuMapper.insert(primaryMenu));
    }
    
    @Test
    public void query() {
        System.out.println(menuMapper.queryById(1).getPrimaryMenuName());
    }
    
    @Test
    public void update() {
        PrimaryMenu primaryMenu = new PrimaryMenu();
        primaryMenu.setPrimaryMenuId(1);
        primaryMenu.setPrimaryMenuName("库房管理");
        primaryMenu.setPrimaryMenuWeight(10);
        System.out.println(menuMapper.update(primaryMenu));
    }
    
    @Test
    public void delete() {
        System.out.println(menuMapper.delete(1));
    }
    
}
