package com.market.scms.service;

import com.market.scms.entity.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/15 9:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StockServiceTest {

    @Resource
    private StockService stockService;
    
    @Test
    public void insertTest() {
        Stock stock = new Stock();
        stock.setStockGoodsId(2L);
        stock.setStockGoodsNum(180);
        System.out.println(stockService.insertStock(stock));
    }
    
    @Test
    public void queryTest() {
        System.out.println(stockService.queryStockByGoodsId(2L).getStockGoodsNum());
        System.out.println(stockService.queryStockById(3l).getStockGoodsNum());
    }
    
    @Test
    public void updateTest() {
        Stock stock = stockService.queryStockByGoodsId(2L);
        stock.setStockGoodsNum(180);
        System.out.println(stockService.updateStock(stock));
    }
    
}
