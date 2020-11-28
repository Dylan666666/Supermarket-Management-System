package com.market.scms.service;

import com.market.scms.entity.RefundStock;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/28 15:58
 */
public interface RefundStockService {
    
    public final static String REFUND_STOCK_LIST_KEY = "refundStockList";

    /**
     * 添加退货库存
     *
     * @param refundStock
     * @return
     */
    int insert(RefundStock refundStock);

    /**
     * 更新退货库存
     *
     * @param refundStock
     * @return
     */
    int update(RefundStock refundStock);

    /**
     * 通过编号查退货库存
     *
     * @param refundCustomerId
     * @param refundCustomerStockGoodsId
     * @return
     */
    RefundStock queryByDoubleId(String refundCustomerId, Long refundCustomerStockGoodsId);

    /**
     * 通过退货编号查询
     *
     * @param refundCustomerId
     * @return
     */
    List<RefundStock> queryByRefundCustomerId(String refundCustomerId);

    /**
     * 通过退货编号查询
     *
     * @param refundCustomerStockGoodsId
     * @return
     */
    List<RefundStock> queryByRefundCustomerStockGoodsId(Long refundCustomerStockGoodsId);

    /**
     * 模糊查询
     *
     * @param refundStockCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<RefundStock> queryByCondition(RefundStock refundStockCondition,
                                       int pageIndex,
                                       int pageSize);

    /**
     * 一键查询
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<RefundStock> queryAll(int pageIndex, int pageSize);
}
