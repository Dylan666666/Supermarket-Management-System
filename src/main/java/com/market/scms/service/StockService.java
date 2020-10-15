package com.market.scms.service;

import com.market.scms.entity.Stock;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/15 8:51
 */
public interface StockService {

    /**
     * 通过库存编号查询库存表
     *
     * @param stockId
     * @return
     */
    Stock queryStockById(Long stockId);

    /**
     * 通过商品单号查询库存表
     *
     * @param stockGoodsId
     * @return
     */
    Stock queryStockByGoodsId(Long stockGoodsId);

    /**
     * 添加新库存表单
     *
     * @param stock
     * @return
     */
    int insertStock(Stock stock);

    /**
     * 更改库存表单信息
     *
     * @param stock
     * @return
     */
    int updateStock(Stock stock);
    
}
