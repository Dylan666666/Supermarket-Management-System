package com.market.scms.mapper;

import com.market.scms.entity.RefundStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/28 15:37
 */
public interface RefundStockMapper {
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
    RefundStock queryByDoubleId(@Param("refundCustomerId") String refundCustomerId,
                          @Param("refundCustomerStockGoodsId") Long refundCustomerStockGoodsId);

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
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<RefundStock> queryByCondition(@Param("refundStockCondition")RefundStock refundStockCondition, 
                                       @Param("rowIndex")int rowIndex,
                                       @Param("pageSize")int pageSize);

    /**
     * 一键查询
     *
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<RefundStock> queryAll(@Param("rowIndex")int rowIndex,
                         @Param("pageSize")int pageSize);
}
