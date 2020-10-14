package com.market.scms.service;

import com.market.scms.entity.GodownEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/13 21:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GodownEntryServiceTest {
    
    @Resource
    private GodownEntryService godownEntryService;
    
    @Test
    public void insertTest() {
        GodownEntry godownEntry = new GodownEntry();
        godownEntry.setGodownEntryDate(new Date());
        godownEntry.setGodownOrderId(2L);
        godownEntry.setGodownEntrySupplierId(1L);
        godownEntry.setGodownEntryStatus(1);
        godownEntry.setGodownEntryPaid(0D);
        godownEntry.setGodownEntryNum(50);
        godownEntry.setGodownEntryGoodsPrice(2.45);
        godownEntry.setGodownEntryGoodsId(2L);
        System.out.println(godownEntryService.insertEntry(godownEntry));
    }
    
    @Test
    public void queryTest() {
        System.out.println(godownEntryService.queryEntryByGoodsId(2L).getGodownEntryDate());
        System.out.println(godownEntryService.queryEntryById(2L).getGodownEntryDate());
        System.out.println(godownEntryService.queryEntryListBySupplierId(1L, 
                0, 100).get(1).getGodownEntryDate());
    }
   
    @Test
    public void updateTest() {
        GodownEntry godownEntry = godownEntryService.queryEntryByGoodsId(2L);
        godownEntry.setGodownEntryDate(new Date());
        System.out.println(godownEntryService.updateEntry(godownEntry));
    }
    
    @Test
    public void queryByStatus() {
        GodownEntry godownEntry = new GodownEntry();
        godownEntry.setGodownEntryStatus(1);
        List<GodownEntry> list = godownEntryService
                .queryEntryListByGodownCondition(godownEntry, 0, 100);
        for (GodownEntry entry : list) {
            System.out.println(entry.getGodownEntryGoodsPrice());
        }
    }
    
}
