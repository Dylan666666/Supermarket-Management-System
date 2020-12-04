package com.market.scms.service;

import com.market.scms.entity.RefundCustomerRecord;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/28 13:35
 */
public interface RefundCustomerRecordService {

    public final static String REFUND_CUSTOMER_RECORD_LIST_KEY = "refundCustomerRecordList";
    
    /**
     * 添加退货记录表
     *
     * @param refundCustomerRecord
     * @return
     */
    int insert(RefundCustomerRecord refundCustomerRecord);

    /**
     * 更新退货记录表
     *
     * @param refundCustomerRecord
     * @return
     */
    int update(RefundCustomerRecord refundCustomerRecord);

    /**
     * 通过ID查询退货记录表
     *
     * @param refundCustomerId
     * @return
     */
    RefundCustomerRecord queryByRefundCustomerId(String refundCustomerId);

    /**
     * 一键查询退货记录表
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<RefundCustomerRecord> queryAll(int pageIndex, int pageSize);

    /**
     * 模糊查询退货记录表
     *
     * @param refundCustomerRecordCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<RefundCustomerRecord> queryByCondition(RefundCustomerRecord refundCustomerRecordCondition, 
                                                int pageIndex, 
                                                int pageSize);

    /**
     * 通过ID查询退货记录表
     *
     * @param refundCustomerOrderId
     * @return
     */
    RefundCustomerRecord queryByOrderId(String refundCustomerOrderId);
}
