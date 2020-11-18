package com.market.scms.mapper;

import com.market.scms.entity.Coupon;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 16:35
 */
public interface CouponMapper {
    /**
     * 添加订货单
     * 
     * @param coupon
     * @return
     */
    int insert(Coupon coupon);

    /**
     * 更新订货单信息
     *
     * @param coupon
     * @return
     */
    int update(Coupon coupon);

    /**
     * 通过ID查询订货单
     * 
     * @param couponId
     * @return
     */
    Coupon queryByCouponId(Long couponId);

    /**
     * 通过发起职工ID查询订货单
     *
     * @param staffId
     * @return
     */
    List<Coupon> queryByStaffId(int staffId);
}
