package com.market.scms.mapper;

import com.market.scms.entity.Stocktaking;
import com.market.scms.enums.StocktakingProfitLossStatusEnum;
import com.market.scms.enums.StocktakingStatusEnum;
import com.market.scms.util.DeliveryIdCreator;
import com.market.scms.util.StocktakingIdCreator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/26 19:14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StocktakingMapperTest {
    
    @Resource
    private StocktakingMapper stocktakingMapper;
    
    @Test
    public void insert() {
        Stocktaking stocktaking = new Stocktaking();
        stocktaking.setStocktakingId(StocktakingIdCreator.get(stocktakingMapper.getCount(StocktakingIdCreator.getDateString())));
        stocktaking.setStocktakingStockGoodsId(1234567890123L);
        stocktaking.setStocktakingTime(new Date());
        stocktaking.setStockNum(100);
        stocktaking.setStocktakingNum(100);
        stocktaking.setStocktakingStatus(StocktakingStatusEnum.SECOND.getState());
        stocktaking.setStocktakingProfitLossStatus(StocktakingProfitLossStatusEnum.NORMAL.getState());
        stocktaking.setStocktakingStaffId(2L);
        System.out.println(stocktakingMapper.insert(stocktaking));
    }

    @Test
    public void query() {
        System.out.println(stocktakingMapper.getCount(StocktakingIdCreator.getDateString()));
        System.out.println(stocktakingMapper
                .queryById(202011260L, 1234567890123L).getStockNum());
        System.out.println(stocktakingMapper.queryAll(0, 100).get(0).getStockNum());
        System.out.println(stocktakingMapper.queryByCondition(new Stocktaking(), 0, 100).get(0).getStockNum());
    }

    @Test
    public void update() {

    }
}
