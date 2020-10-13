package com.market.scms.dao;

import com.market.scms.entity.GoodsList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/13 10:22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodListDaoTest {
    
    @Resource
    private GoodsListDao goodsListDao;

    @Test
    public void insertTest() {
        GoodsList goodsList = new GoodsList();
        goodsList.setDateManufacture(new Date());
        goodsList.setGoodsCategory("牛奶");
        goodsList.setGoodsName("AD钙");
        goodsList.setGoodsOrderId(1L);
        goodsList.setGoodsPrice(1.9);
        goodsList.setShelfLife(180);
        System.out.println(goodsListDao.insertGoods(goodsList));
    }
    
    @Test
    public void query() {
        System.out.println(goodsListDao.queryGoodsById(1L).getGoodsName());
        List<GoodsList> lists = goodsListDao.queryGoodsByOrderId(1L, 0, 100);
        for (GoodsList list : lists) {
            System.out.println(list.getGoodsName());
        }
    }

    @Test
    public void update() {
        GoodsList goodsList = new GoodsList();
        goodsList.setDateManufacture(new Date());
        goodsList.setGoodsCategory("牛奶");
        goodsList.setGoodsName("AD钙");
        goodsList.setGoodsOrderId(1L);
        goodsList.setGoodsPrice(1.8);
        goodsList.setShelfLife(180);
        goodsList.setGoodsId(1L);
        System.out.println(goodsListDao.updateGoods(goodsList));
    }
    
}
