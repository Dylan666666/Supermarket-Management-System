package com.market.scms.service.impl;

import com.market.scms.dao.OrderFormDao;
import com.market.scms.entity.OrderForm;
import com.market.scms.enums.OrderFormStatusStateEnum;
import com.market.scms.exceptions.OrderFormException;
import com.market.scms.service.OrderFomService;
import com.market.scms.util.PageCalculator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/12 13:21
 */
@Service
public class OrderFormServiceImpl implements OrderFomService {
    
    @Resource
    private OrderFormDao orderFormDao;

    /**
     * 通过ID查询订货单
     * 
     * @param orderId
     * @return
     */
    @Override
    public OrderForm queryFormById(Long orderId) throws OrderFormException {
        if (orderId > 0) {
            try {
                OrderForm orderForm = orderFormDao.queryFormById(orderId);
                return orderForm;
            } catch (OrderFormException e) {
                throw new OrderFormException("订单号查询失败");
            }
        } else { 
            throw new OrderFormException("订单号有错");   
        }
    }

    /**
     * 模糊查询订单
     * 
     * @param orderFormCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public List<OrderForm> queryFormByCondition(OrderForm orderFormCondition, int pageIndex, int pageSize) {
        if (orderFormCondition != null && pageIndex >= 0 && pageSize > 0) {
            int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
            List<OrderForm> list = null;
            try {
                list = orderFormDao.queryFormByCondition(orderFormCondition, rowIndex, pageSize);
            } catch (OrderFormException e) {
                throw new OrderFormException("查询失败");
            }
            return list;
        } else {
            throw new OrderFormException("查询条件有误");
        }
    }

    /**
     * 添加订单
     * 
     * @param orderForm
     * @return
     */
    @Override
    public int insertOrderForm(OrderForm orderForm) {
        if (orderForm != null && orderForm.getGoodsName() != null && orderForm.getGoodsCategory() != null
        && orderForm.getGoodsNum() != null && orderForm.getGoodsDetailedDescription() != null) {
            orderForm.setOrderStatus(OrderFormStatusStateEnum.TO_BE_REVIEWED_BY_MANAGER.getState());
            orderForm.setOrderTime(new Date());
            try {
                int res = orderFormDao.insertOrderForm(orderForm);
                if (res == 0) {
                    throw new OrderFormException("添加失败");
                }
                return res;
            } catch (OrderFormException e) {
                throw new OrderFormException("添加失败");
            }
        } else {
            throw new OrderFormException("订单信息不全");
        }
    }

    /**
     * 更改订单信息 （主用于更改状态）
     * 
     * @param orderForm
     * @return
     */
    @Override
    public int updateOrderForm(OrderForm orderForm) {
        if (orderForm != null && orderForm.getOrderStatus() != null) {
            try {
                int res = orderFormDao.updateOrderForm(orderForm);
                if (res != 1) {
                    throw new OrderFormException("更新失败");
                }
                return res;
            } catch (OrderFormException e) {
                throw new OrderFormException("更新失败");
            }
        } else {
            throw new OrderFormException("订单信息不全");
        }
    }
}
