package com.market.scms.mapper;

import com.market.scms.entity.Stock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 21:14
 */
public interface StockMapper {

    /**
     * 添加库存
     * 
     * @param stock
     * @return
     */
    int insert(Stock stock);

    /**
     * 更新库存
     * 
     * @param stock
     * @return
     */
    int update(Stock stock);

    /**
     * 通过产品编号查库存表
     * 
     * @param goodsStockId
     * @return
     */
    Stock queryByGoodsId(Long goodsStockId);

    /**
     * 通过产品编号查库存表
     *
     * @param stockGoodsId
     * @return
     */
    Stock queryById(Long stockGoodsId);

    /**
     * 通过入库编号查询
     * 
     * @param stockExportBillId
     * @return
     */
    Stock queryByExportBillId(String stockExportBillId);

    /**
     * 模糊查询
     * 
     * @param stockCondition
     * @return
     */
    List<Stock> queryByCondition(Stock stockCondition);

    /**
     * 一键查询
     * 
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Stock> queryAll(@Param("rowIndex")int rowIndex,
                         @Param("pageSize")int pageSize);
}
