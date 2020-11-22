package com.market.scms.service.impl;

import com.market.scms.entity.Coupon;
import com.market.scms.enums.CouponStatusStateEnum;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.mapper.CouponMapper;
import com.market.scms.service.CouponService;
import com.market.scms.util.PageCalculator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 17:02
 */
@Service
public class CouponServiceImpl implements CouponService {
    
    @Resource
    private CouponMapper couponMapper;
    
    @Override
    public int insert(Coupon coupon) throws WareHouseManagerException {
        if (coupon != null && coupon.getCouponGoodsId() != null && coupon.getCouponNum() != null &&
        coupon.getCouponStaffId() != null && coupon.getCouponUnitId() != null) {
            try {
                coupon.setCouponTime(new Date());
                coupon.setCouponStatus(CouponStatusStateEnum.ORDERING.getState());
                int res = couponMapper.insert(coupon);
                if (res == 0) {
                    throw new WareHouseManagerException("添加订单失败");
                }
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("添加订单失败");
            }
        } else {
            throw new WareHouseManagerException("添加订单失败");
        }
    }

    @Override
    public int update(Coupon coupon) throws WareHouseManagerException {
        if (coupon != null && coupon.getCouponId() != null && coupon.getCouponStatus() != null) {
            try {
                int res = couponMapper.update(coupon);
                if (res == 0) {
                    throw new WareHouseManagerException("更新订单失败");
                }
                return res;   
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("更新订单失败");
            }
        } else {
            throw new WareHouseManagerException("更新订单失败");
        }
    }

    @Override
    public Coupon queryByCouponId(Long couponId) throws WareHouseManagerException {
        if (couponId != null && couponId > 0) {
            try {
                Coupon coupon = couponMapper.queryByCouponId(couponId);
                return coupon;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询订单失败");
            }
        } else {
            throw new WareHouseManagerException("不具备查询条件");
        }
    }

    @Override
    public List<Coupon> queryByStaffId(int staffId) throws WareHouseManagerException {
        if (staffId > 0) {
            try {
                List<Coupon> res = couponMapper.queryByStaffId(staffId);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询订单失败");
            }
        } else {
            throw new WareHouseManagerException("不具备查询条件");
        }
    }

    @Override
    public List<Coupon> queryAll(int pageIndex, int pageSize) throws WareHouseManagerException {
        pageIndex = pageIndex >= 0 ? pageIndex : 0;
        pageSize = pageSize > 0 ? pageSize : 100;
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
        try {
            List<Coupon> res = couponMapper.queryAll(rowIndex,  pageSize);
            return res;
        } catch (WareHouseManagerException e) {
            throw new WareHouseManagerException("查询失败");
        }
    }
}
