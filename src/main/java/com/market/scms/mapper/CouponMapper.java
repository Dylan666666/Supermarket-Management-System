package com.market.scms.mapper;

import com.market.scms.entity.Coupon;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
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

    /**
     * 一键查询
     * 
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Coupon> queryAll(@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 订货单模糊查询
     *
     * @param couponCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Coupon> queryByCondition(@Param("couponCondition")Coupon couponCondition,
                                  @Param("rowIndex")int rowIndex,
                                  @Param("pageSize")int pageSize);
}
