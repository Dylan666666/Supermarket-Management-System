package com.market.scms.service;

import com.market.scms.entity.Stocktaking;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/26 20:01
 */
public interface StocktakingService {

    public final static String STOCKTAKING_LIST_KEY = "StocktakingList";

    /**
     * 添加盘点单
     *
     * @param stocktaking
     * @return
     */
    int insert(Stocktaking stocktaking);

    /**
     * 更新盘点单信息
     *
     * @param stocktaking
     * @return
     */
    int update(Stocktaking stocktaking);

    /**
     * 通过ID查询盘点单
     *
     * @param stockTakingId
     * @param stockTakingStockGoodsId
     * @return
     */
    Stocktaking queryById(Long stockTakingId, Long stockTakingStockGoodsId);

    /**
     * 一键查询盘点单 
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<Stocktaking> queryAll(int pageIndex, int pageSize);

    /**
     * 模糊查询
     *
     * @param stocktakingCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<Stocktaking> queryByCondition(Stocktaking stocktakingCondition,
                                       int pageIndex,
                                       int pageSize);
    
}
