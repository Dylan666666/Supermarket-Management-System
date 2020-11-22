package com.market.scms.mapper;

import com.market.scms.entity.Goods;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 12:28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsMapperTest {
    
    @Resource
    private GoodsMapper goodsMapper;
    
    @Test
    public void insert() {
        Goods goods = new Goods();
        goods.setGoodsId(123L);
        goods.setGoodsBrand("娃哈哈");
        goods.setGoodsCategoryId(2);
        goods.setGoodsName("AD钙");
        goods.setGoodsSpecifications("200ML");
        System.out.println(goodsMapper.insertGoods(goods));
    }
    
    @Test
    public void query() {
        Goods goods = goodsMapper.queryAll().get(0);
        Goods g = new Goods();
        g.setGoodsId(123L);
        System.out.println(goods.getGoodsName());
        System.out.println(goodsMapper.queryByCondition(g, 0, 100).get(0).getGoodsName());
    }
    
    @Test
    public void update() {
        Goods goods = goodsMapper.queryAll().get(0);
        goods.setGoodsSpecifications("200ML原味");
        System.out.println(goodsMapper.updateGoods(goods));
    } 
    
    @Test
    public void delete() {
        System.out.println(goodsMapper.deleteGoods(123L));
    }
    
}
