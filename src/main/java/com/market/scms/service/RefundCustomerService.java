package com.market.scms.service;

import com.market.scms.entity.RefundCustomer;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/28 13:06
 */
public interface RefundCustomerService {

    public final static String REFUND_CUSTOMER_LIST_KEY = "refundCustomerList";
    
    /**
     * 添加退货联系表
     *
     * @param refundCustomer
     * @return
     */
    int insert(RefundCustomer refundCustomer);

    /**
     * 更新退货联系表
     *
     * @param refundCustomer
     * @return
     */
    int update(RefundCustomer refundCustomer);

    /**
     * 通过ID查询退货联系表
     *
     * @param refundCustomerId
     * @return
     */
    List<RefundCustomer> queryByRefundId(String refundCustomerId);

    /**
     * 通过商品ID退货联系表
     *
     * @param refundCustomerId
     * @param refundCustomerStockGoodsId
     * @return
     */
    RefundCustomer queryByDoubleId(String refundCustomerId, Long refundCustomerStockGoodsId);

    /**
     * 一键查询退货联系表
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<RefundCustomer> queryAll(int pageIndex, int pageSize);
}
