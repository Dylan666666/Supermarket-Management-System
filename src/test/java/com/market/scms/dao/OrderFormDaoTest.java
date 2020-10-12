package com.market.scms.dao;

import com.market.scms.entity.OrderForm;
import com.market.scms.enums.OrderFormStatusStateEnum;
import com.market.scms.enums.OrderTypeStateEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/11 22:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderFormDaoTest {
    
    @Resource
    private OrderFormDao orderFormDao;

    @Test
    public void insertTest() {
        OrderForm orderForm = new OrderForm();
        orderForm.setGoodsName("AD钙");
        orderForm.setGoodsCategory("牛奶");
        orderForm.setGoodsNum(100);
        orderForm.setOrderStatus(1);
        orderForm.setOrderTime(new Date());
        System.out.println(orderFormDao.insertOrderForm(orderForm));
    }
    
    @Test
    public void query() {
        System.out.println(orderFormDao.queryFormById(1L).getGoodsName());
        OrderForm form = new OrderForm();
        form.setOrderStatus(OrderFormStatusStateEnum.TO_BE_REVIEWED_BY_MANAGER.getState());
        System.out.println(orderFormDao.queryFormByCondition(form, 0, 100).get(0).getGoodsName());
    }
    
    @Test
    public void update() {
        OrderForm orderForm = new OrderForm();
        orderForm.setOrderId(1L);
        orderForm.setOrderStatus(2);
        System.out.println(orderFormDao.updateOrderForm(orderForm));
    }
    
}
