package com.market.scms.service;

import com.market.scms.entity.OrderForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/12 14:36
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderFormServiceTest {
    
    @Resource
    private OrderFomService orderFomService;
    
    @Test
    public void insertTest() {
        OrderForm orderForm = new OrderForm();
        orderForm.setGoodsName("维他豆钙");
        orderForm.setGoodsCategory("牛奶");
        orderForm.setGoodsNum(50);
        orderForm.setOrderStatus(1);
        orderForm.setOrderTime(new Date());
        System.out.println(orderFomService.insertOrderForm(orderForm));
    }
    
    @Test
    public void queryTest() {
        System.out.println(orderFomService.queryFormById(1L).getGoodsName());
        List<OrderForm> list = orderFomService.queryFormByCondition(new OrderForm(), 0, 100);
        for (OrderForm orderForm : list) {
            System.out.println(orderForm.getGoodsName());
        }
    }
    
    @Test
    public void updateTest() {
        OrderForm orderForm = orderFomService.queryFormById(1L);
        orderForm.setOrderTime(new Date());
        System.out.println(orderFomService.updateOrderForm(orderForm));
    }
    
}
