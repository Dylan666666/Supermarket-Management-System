package com.market.scms.entity;

import java.util.Date;

/**
 * 订货单
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/18 10:13
 */
public class Coupon {
    private Long couponId;
    private Long couponGoodsId;
    private Integer couponUnitId;
    private Integer couponNum;
    private Date couponTime;
    private Integer couponStatus;
    private Integer couponStaffId;

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getCouponGoodsId() {
        return couponGoodsId;
    }

    public void setCouponGoodsId(Long couponGoodsId) {
        this.couponGoodsId = couponGoodsId;
    }

    public Integer getCouponUnitId() {
        return couponUnitId;
    }

    public void setCouponUnitId(Integer couponUnitId) {
        this.couponUnitId = couponUnitId;
    }

    public Integer getCouponNum() {
        return couponNum;
    }

    public void setCouponNum(Integer couponNum) {
        this.couponNum = couponNum;
    }

    public Date getCouponTime() {
        return couponTime;
    }

    public void setCouponTime(Date couponTime) {
        this.couponTime = couponTime;
    }

    public Integer getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(Integer couponStatus) {
        this.couponStatus = couponStatus;
    }

    public Integer getCouponStaffId() {
        return couponStaffId;
    }

    public void setCouponStaffId(Integer couponStaffId) {
        this.couponStaffId = couponStaffId;
    }
}
