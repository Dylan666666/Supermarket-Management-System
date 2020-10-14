package com.market.scms.dao;

import com.market.scms.entity.StockJournal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/14 21:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StockJournalDaoTest {

    @Resource
    private StockJournalDao stockJournalDao;
    
    @Test
    public void insertTest() {
        StockJournal stockJournal = new StockJournal();
        stockJournal.setStockJournalGoodsId(1L);
        stockJournal.setStockJournalDate(new Date());
        stockJournal.setStockJournalGodownEntryId(1L);
        stockJournal.setStockJournalGoodsPrice(1.8);
        stockJournal.setStockJournalNum(100);
        stockJournal.setStockJournalPaid(180D);
        stockJournal.setStockJournalSupplierId(1L);
        System.out.println(stockJournalDao.insertStockJournal(stockJournal));
    }
    
    @Test
    public void queryTest() {
        System.out.println(stockJournalDao.queryStockJournalById(1L).getStockJournalNum());
        System.out.println(stockJournalDao.queryStockJournalList().get(0).getStockJournalNum());
        StockJournal stockJournal = new StockJournal();
        stockJournal.setStockJournalGoodsId(1L);
        System.out.println(stockJournalDao.queryStockJournalListByCondition(stockJournal, 0, 100)
                .get(0).getStockJournalNum());
    }
    
}
