package com.market.scms.mapper;

import com.market.scms.entity.Retail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/25 10:45
 */
public interface RetailMapper {
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
    Retail queryByGoodsId(@Param("retailId") String retailId, @Param("retailStockGoodsId") Long retailStockGoodsId);

    /**
     * 一键查询订货单
     *
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Retail> queryAll(@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
}
