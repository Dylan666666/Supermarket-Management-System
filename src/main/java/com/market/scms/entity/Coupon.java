package com.market.scms.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 订货单
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/18 10:13
 */
public class Coupon implements Serializable {
    private static final long serialVersionUID = 6721372162208813658L;
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

    @Override
    public String toString() {
        return "Coupon{" +
                "couponId=" + couponId +
                ", couponGoodsId=" + couponGoodsId +
                ", couponUnitId=" + couponUnitId +
                ", couponNum=" + couponNum +
                ", couponTime=" + couponTime +
                ", couponStatus=" + couponStatus +
                ", couponStaffId=" + couponStaffId +
                '}';
    }
}
