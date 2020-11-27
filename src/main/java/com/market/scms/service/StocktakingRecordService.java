package com.market.scms.service;

import com.market.scms.entity.StocktakingRecord;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/26 21:50
 */
public interface StocktakingRecordService {

    public final static String STOCKTAKING_RECORD_LIST_KEY = "StocktakingRecordList";
    
    /**
     * 添加盘点详情单
     *
     * @param stocktakingRecord
     * @return
     */
    int insert(StocktakingRecord stocktakingRecord);

    /**
     * 更新盘点详情单信息
     *
     * @param stocktakingRecord
     * @return
     */
    int update(StocktakingRecord stocktakingRecord);

    /**
     * 通过ID查询盘点详情单
     *
     * @param stocktakingId
     * @return
     */
    StocktakingRecord queryById(Long stocktakingId);

    /**
     * 一键查询盘点详情单 
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<StocktakingRecord> queryAll(int pageIndex,int pageSize);

    /**
     * 模糊查询盘点详情单
     *
     * @param condition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<StocktakingRecord> queryByCondition(StocktakingRecord condition, int pageIndex, int pageSize);

    /**
     * 查询是否有正在盘点的订单
     *
     * @param state
     * @return
     */
    int queryStocktakingCount(int state);
}
