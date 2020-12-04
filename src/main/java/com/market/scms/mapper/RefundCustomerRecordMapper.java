package com.market.scms.mapper;

import com.market.scms.entity.RefundCustomerRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/28 13:17
 */
public interface RefundCustomerRecordMapper {
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
     * 通过ID查询退货记录表
     *
     * @param refundCustomerOrderId
     * @return
     */
    RefundCustomerRecord queryByOrderId(String refundCustomerOrderId);

    /**
     * 一键查询退货记录表
     *
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<RefundCustomerRecord> queryAll(@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 模糊查询退货记录表
     *
     * @param refundCustomerRecordCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<RefundCustomerRecord> queryByCondition(
            @Param("refundCustomerRecordCondition") RefundCustomerRecord refundCustomerRecordCondition,
            @Param("rowIndex") int rowIndex,
            @Param("pageSize") int pageSize);
}
