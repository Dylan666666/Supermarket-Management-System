package com.market.scms.mapper;

import com.market.scms.entity.Stocktaking;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/26 15:31
 */
public interface StocktakingMapper {
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
    Stocktaking queryById(@Param("stocktakingId") Long stockTakingId,
                          @Param("stocktakingStockGoodsId") Long stockTakingStockGoodsId);

    /**
     * 一键查询盘点单 
     *
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Stocktaking> queryAll(@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 模糊查询
     * 
     * @param stocktakingCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Stocktaking> queryByCondition(@Param("stocktakingCondition")Stocktaking stocktakingCondition,
                                       @Param("rowIndex") int rowIndex, 
                                       @Param("pageSize") int pageSize);

    /**
     * 获取今日盘点次数
     * 
     * @param dateFormat
     * @return
     */
    int getCount(String dateFormat);
}
