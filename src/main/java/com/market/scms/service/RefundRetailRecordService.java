package com.market.scms.service;

import com.market.scms.entity.RefundRetailRecord;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/28 15:20
 */
public interface RefundRetailRecordService {
    
    public final static String REFUND_RETAIL_RECORD_LIST_KEY = "refundRetailRecordList";

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
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<RefundRetailRecord> queryAll(int pageIndex, int pageSize);

    /**
     * 模糊查询退货详情表
     *
     * @param refundRetailRecordCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<RefundRetailRecord> queryByCondition(
            RefundRetailRecord refundRetailRecordCondition,
            int pageIndex,
            int pageSize);
}
