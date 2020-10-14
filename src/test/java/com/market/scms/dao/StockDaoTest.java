package com.market.scms.dao;

import com.market.scms.entity.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/14 20:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StockDaoTest {

    @Resource
    private StockDao stockDao;
    
    @Test
    public void insertTest() {
        Stock stock = new Stock();
        stock.setStockGoodsId(1L);
        stock.setStockGoodsNum(100);
        System.out.println(stockDao.insertStock(stock));
    }
    
    @Test
    public void queryTest() {
        System.out.println(stockDao.queryStockByGoodsId(1L).getStockGoodsNum());
        System.out.println(stockDao.queryStockById(2L).getStockGoodsNum());
    }
    
    @Test
    public void updateTest() {
        Stock stock = stockDao.queryStockByGoodsId(1L);
        stock.setStockGoodsNum(100);
        System.out.println(stockDao.updateStock(stock));
    }
    
}
