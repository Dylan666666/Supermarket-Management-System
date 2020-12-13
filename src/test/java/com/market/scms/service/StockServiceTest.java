package com.market.scms.service;

import com.market.scms.entity.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/22 13:02
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StockServiceTest {
    
    @Resource
    private StockService stockService;
    @Resource
    private CacheService cacheService;
    
    @Test
    public void update() {
        Stock stock = new Stock();
        stock.setStockGoodsId(1L);
        stock.setStockGoodsPrice(1.41);
        System.out.println(stockService.update(stock));
    }
    
    @Test
    public void clear() {
        cacheService.removeFromCache(StocktakingService.STOCKTAKING_LIST_KEY);
    }
    
}
