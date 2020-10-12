package com.market.scms.service;

import com.market.scms.entity.OrderForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/12 13:18
 */
public interface OrderFomService {
    /**
     * 通过订单编号查询订单
     *
     * @param orderId
     * @return
     */
    OrderForm queryFormById(@Param("orderId") Long orderId);

    /**
     * 模糊查询订单
     *
     * @param orderFormCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<OrderForm> queryFormByCondition(OrderForm orderFormCondition, int pageIndex, int pageSize);

    /**
     * 添加新订单
     *
     * @param orderForm
     * @return
     */
    int insertOrderForm(OrderForm orderForm);

    /**
     * 更改订单信息（用于更改状态）
     *
     * @param orderForm
     * @return
     */
    int updateOrderForm(OrderForm orderForm);
}
