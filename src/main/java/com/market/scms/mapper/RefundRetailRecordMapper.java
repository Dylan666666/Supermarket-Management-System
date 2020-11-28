package com.market.scms.mapper;

import com.market.scms.entity.RefundRetailRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/28 15:06
 */
public interface RefundRetailRecordMapper {
    /**
     * 添加退货详情表
     *
     * @param refundRetailRecord
     * @return
     */
    int insert(RefundRetailRecord refundRetailRecord);

    /**
     * 更新退货详情表
     *
     * @param refundRetailRecord
     * @return
     */
    int update(RefundRetailRecord refundRetailRecord);

    /**
     * 通过ID查询退货详情表
     *
     * @param refundRetailId
     * @return
     */
    RefundRetailRecord queryByRefundRetailId(String refundRetailId);

    /**
     * 一键查询退货详情表
     *
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<RefundRetailRecord> queryAll(@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 模糊查询退货详情表
     *
     * @param refundRetailRecordCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<RefundRetailRecord> queryByCondition(
            @Param("refundRetailRecordCondition") RefundRetailRecord refundRetailRecordCondition,
            @Param("rowIndex") int rowIndex,
            @Param("pageSize") int pageSize);
}
