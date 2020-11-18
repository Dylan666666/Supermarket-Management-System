package com.market.scms.mapper;

import com.market.scms.entity.Coupon;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 16:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CouponMapperTest {
    
    @Resource
    private CouponMapper couponMapper;
    
    @Test
    public void insert() {
        Coupon coupon = new Coupon();
        coupon.setCouponGoodsId(1L);
        coupon.setCouponNum(100);
        coupon.setCouponStaffId(25);
        coupon.setCouponStatus(0);
        coupon.setCouponTime(new Date());
        coupon.setCouponUnitId(1);
        System.out.println(couponMapper.insert(coupon));
    }
    
    
    
    @Test
    public void query() {
        System.out.println(couponMapper.queryByCouponId(1L).getCouponId());
        System.out.println(couponMapper.queryByStaffId(25).get(0).getCouponId());
    }
    
    @Test
    public void update() {
        Coupon coupon = couponMapper.queryByCouponId(1L);
        coupon.setCouponStatus(1);
        System.out.println(couponMapper.update(coupon));
    }
    
}
