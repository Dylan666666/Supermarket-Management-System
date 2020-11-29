package com.market.scms.mapper;

import com.market.scms.entity.Stock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 21:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StockMapperTest {
    
    @Resource
    private StockMapper stockMapper;
    
    @Test
    public void insert() {
        Stock stock = new Stock();
        stock.setStockId(1);
        stock.setGoodsStockId(1234567894321L);
        stock.setStockUnitId(1);
        stock.setStockGoodsBatchNumber(1);
        stock.setStockGoodsProductionDate(new Date());
        stock.setStockGoodsShelfLife(180);
        stock.setStockGoodsPrice(1.9);
        stock.setStockInventory(1000);
        stock.setStockExportBillId("123");
        System.out.println(stockMapper.insert(stock));
    }
    
    @Test
    public void query() {
        System.out.println(stockMapper.queryAll(0, 10000).get(0).getGoodsStockId());
        Stock stock = new Stock();
        stock.setStockExportBillId("123");
        System.out.println(stockMapper.queryByCondition(stock).get(0).getGoodsStockId());
        System.out.println(stockMapper.queryByExportBillId("123").getGoodsStockId());
        System.out.println(stockMapper.queryByGoodsId(1234567894321L).get(0).getGoodsStockId());
    }
    
    @Test
    public void update() {
        Stock stock = new Stock();
        stock.setStockGoodsId(1L);
        stock.setStockGoodsPrice(1.41);
        System.out.println(stockMapper.update(stock));
    }
    
}
