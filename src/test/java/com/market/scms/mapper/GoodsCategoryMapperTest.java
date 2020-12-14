package com.market.scms.mapper;

import com.market.scms.entity.GoodsCategory;
import com.market.scms.entity.Unit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 13:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsCategoryMapperTest {

    @Resource
    private GoodsCategoryMapper goodsCategoryMapper;

    @Test
    public void insert() {
        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory.setCategoryId(3);
        goodsCategory.setCategoryName("床上用品");
        System.out.println(goodsCategoryMapper.insert(goodsCategory));
    }

    @Test
    public void query() {
        System.out.println(goodsCategoryMapper.queryAll().get(0).getCategoryName());
    }
    
    @Test
    public void update() {
        GoodsCategory goodsCategory = new GoodsCategory();
        goodsCategory = goodsCategoryMapper.queryAll().get(0);
        goodsCategory.setStocktakingStaffId(2);
        System.out.println(goodsCategoryMapper.update(goodsCategory));
    }
    
}
