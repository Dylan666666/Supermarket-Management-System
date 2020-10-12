package com.market.scms.dao;

import com.market.scms.entity.OrderForm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/11 21:35
 */
public interface OrderFormDao {
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
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<OrderForm> queryFormByCondition(@Param("orderFormCondition") OrderForm orderFormCondition,
                                            @Param("rowIndex") int rowIndex,
                                            @Param("pageSize") int pageSize);

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
