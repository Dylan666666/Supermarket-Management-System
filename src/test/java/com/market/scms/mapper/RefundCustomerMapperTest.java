package com.market.scms.mapper;

import com.market.scms.entity.RefundCustomer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/28 16:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RefundCustomerMapperTest {
    
    @Resource
    private RefundCustomerMapper refundCustomerMapper;
    
    @Test
    public void insert() {
        RefundCustomer refundCustomer = new RefundCustomer();
    }

    @Test
    public void query() {
        RefundCustomer refundCustomer = new RefundCustomer();

    }

    @Test
    public void update() {
        RefundCustomer refundCustomer = new RefundCustomer();

    }
    
    
}
