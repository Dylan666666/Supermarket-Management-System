package com.market.scms.service;

import com.market.scms.entity.Unit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/30 17:19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UnitServiceTest {
    @Resource
    private UnitService unitService;
    
    @Test
    public void test() {
        List<Unit> unitList = unitService.queryAll();
        for (Unit unit : unitList) {
            System.out.println(unit.getUnitName());
        }
    }
}
