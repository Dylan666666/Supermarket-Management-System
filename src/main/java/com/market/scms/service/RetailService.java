package com.market.scms.service;

import com.market.scms.entity.Retail;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/25 11:06
 */
public interface RetailService {

    public final static String RETAIL_LIST_KEY = "retailList";
    
    /**
     * 添加订货单
     *
     * @param retail
     * @return
     */
    int insert(Retail retail);

    /**
     * 更新订货单
     *
     * @param retail
     * @return
     */
    int update(Retail retail);

    /**
     * 通过ID查询订货单
     *
     * @param retailId
     * @return
     */
    List<Retail> queryByRetailId(String retailId);

    /**
     * 通过两个主键查询订货单
     *
     * @param retailId
     * @param retailStockGoodsId
     * @return
     */
    Retail queryByGoodsId(String retailId, Long retailStockGoodsId);

    /**
     * 一键查询订货单
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<Retail> queryAll(int pageIndex, int pageSize);
}
