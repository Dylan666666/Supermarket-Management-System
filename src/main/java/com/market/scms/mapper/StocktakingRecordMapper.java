package com.market.scms.mapper;

import com.market.scms.entity.StocktakingRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/26 21:15
 */
public interface StocktakingRecordMapper {
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
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<StocktakingRecord> queryAll(@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 模糊查询盘点详情单
     *
     * @param condition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<StocktakingRecord> queryByCondition(@Param("condition")StocktakingRecord condition, 
                                             @Param("rowIndex") int rowIndex, 
                                             @Param("pageSize") int pageSize);

    /**
     * 查询是否有正在盘点的订单
     * 
     * @param state
     * @return
     */
    int queryStocktakingCount(int state);

    /**
     * 获取今日盘点次数
     *
     * @param dateFormat
     * @return
     */
    int getCount(String dateFormat);
}
