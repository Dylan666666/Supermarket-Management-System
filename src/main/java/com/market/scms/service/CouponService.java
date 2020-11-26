package com.market.scms.service;

import com.market.scms.entity.Coupon;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 17:01
 */
public interface CouponService {

    public final static String COUPON_LIST_KEY = "couponList";
    
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

    /**
     * 一键查询
     *
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<Coupon> queryAll(int pageIndex, int pageSize);

    /**
     * 订货单模糊查询
     *
     * @param couponCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    List<Coupon> queryByCondition(Coupon couponCondition, int pageIndex, int pageSize);
}
