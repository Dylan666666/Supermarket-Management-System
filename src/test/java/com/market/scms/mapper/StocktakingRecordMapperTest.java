package com.market.scms.mapper;

import com.market.scms.entity.StocktakingRecord;
import com.market.scms.util.StocktakingIdCreator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/26 21:38
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StocktakingRecordMapperTest {
    
    @Resource
    private StocktakingRecordMapper stocktakingRecordMapper;
    
    @Test
    public void insert() {
        StocktakingRecord stocktakingRecord = new StocktakingRecord();
        stocktakingRecord.setStocktakingId(202011260L);
        stocktakingRecord.setStocktakingLaunchedStaffId(2L);
        stocktakingRecord.setStocktakingProfitLossPrice(1000D);
        stocktakingRecord.setStocktakingLaunchedDate(new Date());
        System.out.println(stocktakingRecordMapper.insert(stocktakingRecord));
    }

    @Test
    public void query() {
        System.out.println(stocktakingRecordMapper.queryAll(0, 1).get(0).getStocktakingAllStatus());
        System.out.println(stocktakingRecordMapper
                .queryByCondition(new StocktakingRecord(), 0, 1).get(0).getStocktakingAllStatus());
        System.out.println(stocktakingRecordMapper.queryById(202011260L).getStocktakingAllStatus());
    }

    @Test
    public void update() {
        StocktakingRecord stocktakingRecord = stocktakingRecordMapper.queryById(202011260L);
        stocktakingRecord.setStocktakingSubmitStaffId(2L);
        stocktakingRecord.setStocktakingCommitDate(new Date());
        System.out.println(stocktakingRecordMapper.update(stocktakingRecord));
    }
    
    @Test
    public void create() {
        System.out.println(StocktakingIdCreator
                .get(stocktakingRecordMapper.getCount(StocktakingIdCreator.getDateString())));
    }
    
}
