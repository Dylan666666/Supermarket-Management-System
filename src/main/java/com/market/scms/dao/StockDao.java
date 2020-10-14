package com.market.scms.dao;

import com.market.scms.entity.Stock;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/14 20:17
 */
public interface StockDao {
    /**
     * 通过库存编号查询库存表
     *
     * @param stockId
     * @return
     */
    Stock queryStockById(@Param("stockId") Long stockId);

    /**
     * 通过商品单号查询库存表
     *
     * @param stockGoodsId
     * @return
     */
    Stock queryStockByGoodsId(@Param("stockGoodsId") Long stockGoodsId);

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
