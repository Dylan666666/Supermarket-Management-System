package com.market.scms.service;

import com.market.scms.entity.GoodsList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/13 13:33
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsListServiceTest {
    
    @Resource
    private GoodsListService goodsListService;

    @Test
    public void insertTest() {
        GoodsList goodsList = new GoodsList();
        goodsList.setDateManufacture(new Date());
        goodsList.setGoodsCategory("牛奶");
        goodsList.setGoodsName("维他豆钙");
        goodsList.setGoodsOrderId(2L);
        goodsList.setGoodsPrice(2.5);
        goodsList.setShelfLife(180);
        System.out.println(goodsListService.insertGoods(goodsList));
    }
    
    @Test
    public void query() {
        System.out.println(goodsListService.queryGoodsById(2L).getGoodsName());
        System.out.println(goodsListService.queryGoodsByOrderId(2L, 0, 100)
                .get(0).getGoodsName());
    }
    
    @Test
    public void update() {
        GoodsList goodsList = goodsListService.queryGoodsById(2L);
        goodsList.setGoodsPrice(2.45);
        System.out.println(goodsListService.updateGoods(goodsList));
    }
    
}
