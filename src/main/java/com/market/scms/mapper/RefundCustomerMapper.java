package com.market.scms.mapper;

import com.market.scms.entity.RefundCustomer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/27 22:05
 */
public interface RefundCustomerMapper {

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
    RefundCustomer queryByDoubleId(@Param("refundCustomerId") String refundCustomerId, 
                                   @Param("refundCustomerStockGoodsId") Long refundCustomerStockGoodsId);

    /**
     * 一键查询退货联系表
     *
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<RefundCustomer> queryAll(@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);
    
}
