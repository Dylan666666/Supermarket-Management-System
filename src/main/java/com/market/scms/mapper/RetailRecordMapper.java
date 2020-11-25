package com.market.scms.mapper;

import com.market.scms.entity.RetailRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/25 12:26
 */
public interface RetailRecordMapper {
    /**
     * 添加订货详情单
     *
     * @param retailRecord
     * @return
     */
    int insert(RetailRecord retailRecord);

    /**
     * 更新订货详情单
     *
     * @param retailRecord
     * @return
     */
    int update(RetailRecord retailRecord);

    /**
     * 通过ID查询订货详情单
     *
     * @param retailId
     * @return
     */
    RetailRecord queryByRetailId(String retailId);

    /**
     * 一键查询订货详情单
     *
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<RetailRecord> queryAll(@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 模糊查询订货详情单
     *
     * @param retailRecordCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<RetailRecord> queryByCondition(@Param("retailRecordCondition") RetailRecord retailRecordCondition,
                                          @Param("rowIndex") int rowIndex,
                                          @Param("pageSize") int pageSize);
}
