package com.market.scms.dao;

import com.market.scms.entity.GodownEntry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/13 20:36
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GodownEntryDaoTest {
    
    @Resource
    private GodownEntryDao godownEntryDao;
    
    @Test
    public void insertTest() {
        GodownEntry godownEntry = new GodownEntry();
        godownEntry.setGodownEntryDate(new Date());
        godownEntry.setGodownOrderId(1L);
        godownEntry.setGodownEntrySupplierId(1L);
        godownEntry.setGodownEntryStatus(1);
        godownEntry.setGodownEntryPaid(0D);
        godownEntry.setGodownEntryNum(100);
        godownEntry.setGodownEntryGoodsPrice(1.8);
        godownEntry.setGodownEntryGoodsId(1L);
        System.out.println(godownEntryDao.insertEntry(godownEntry));
    }
    
    @Test
    public void queryTest() {
        System.out.println(godownEntryDao.queryEntryByGoodsId(1L).getGodownEntryDate());
        System.out.println(godownEntryDao.queryEntryById(1L).getGodownEntryDate());
        System.out.println(godownEntryDao.queryEntryListBySupplierId(1L, 0, 100).get(0).getGodownEntryDate());
    }
    
    @Test
    public void updateTest() {
        GodownEntry godownEntry = godownEntryDao.queryEntryByGoodsId(1L);
        godownEntry.setGodownEntryStatus(1);
        System.out.println(godownEntryDao.updateEntry(godownEntry));
    }
    
    @Test
    public void queryByCondition() {
        GodownEntry godownEntry = new GodownEntry();
        godownEntry.setGodownEntryStatus(1);
        System.out.println(godownEntryDao.queryEntryListByGodownCondition(godownEntry, 0, 100).get(0).getGodownEntryDate());
    }
    
}
