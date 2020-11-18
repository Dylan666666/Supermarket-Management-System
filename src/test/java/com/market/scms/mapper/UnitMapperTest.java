package com.market.scms.mapper;

import com.market.scms.entity.Unit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 13:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UnitMapperTest {
    
    @Resource
    private UnitMapper unitMapper;
    
    @Test
    public void insert() {
        Unit unit = new Unit();
        unit.setUnitId(6);
        unit.setUnitName("张");
        System.out.println(unitMapper.insert(unit));
    }
    
    @Test
    public void query() {
        System.out.println(unitMapper.queryAll().get(0).getUnitName());
    }
    
}
