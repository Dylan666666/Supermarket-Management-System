package com.market.scms.service;

import com.market.scms.entity.Coupon;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/22 19:38
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CouponServiceTest {

    @Resource
    private CouponService couponService;
    
    @Test
    public void insert() {
        Coupon coupon = new Coupon();
        coupon.setCouponGoodsId(1234567890123L);
        coupon.setCouponUnitId(1);
        coupon.setCouponNum(1000);
        coupon.setCouponStaffId(26);
        System.out.println(couponService.insert(coupon));
    }
    
}
