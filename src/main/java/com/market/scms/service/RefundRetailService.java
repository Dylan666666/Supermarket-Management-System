package com.market.scms.service;

import com.market.scms.entity.RefundRetail;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/28 14:46
 */
public interface RefundRetailService {
    
    public final static String REFUND_RETAIL_LIST_KEY = "refundRetailList";

    /**
     * 添加退货单详情表
     *
     * @param refundRetail
     * @return
     */
    int insert(RefundRetail refundRetail);

    /**
     * 更新退货单详情表
     *
     * @param refundRetail
     * @return
     */
    int update(RefundRetail refundRetail);

    /**
     * 通过ID查询退货单详情表
     *
     * @param refundRetailId
     * @return
     */
    List<RefundRetail> queryByRefundRetailId(String refundRetailId);

    /**
     * 通过ID查询退货单详情表
     *
     * @param retailStockGoodsId
     * @return
     */
    List<RefundRetail> queryByStockGoodsId(Long retailStockGoodsId);

    /**
     * 通过ID查询退货单详情表
     *
     * @param refundCustomerId
     * @return
     */
    List<RefundRetail> queryByRefundCustomerId(String refundCustomerId);

    /**
     * 通过商品ID退货单详情表
     *
     * @param refundRetailId
     * @param retailStockGoodsId
     * @param refundCustomerId
     * @return
     */
    RefundRetail queryByTribeId(String refundRetailId,
                                Long retailStockGoodsId,
                                String refundCustomerId);

    /**
     * 一键查询退货单详情表
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<RefundRetail> queryAll(int pageIndex, int pageSize);
}
