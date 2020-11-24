package com.market.scms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.cache.JedisUtil;
import com.market.scms.entity.Coupon;
import com.market.scms.enums.CouponStatusStateEnum;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.mapper.CouponMapper;
import com.market.scms.service.CacheService;
import com.market.scms.service.CouponService;
import com.market.scms.util.PageCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Resource
    private JedisUtil.Keys jedisKeys;
    @Resource
    private JedisUtil.Strings jedisStrings;
    @Resource
    private CacheService cacheService;

    private static Logger logger = LoggerFactory.getLogger(CouponServiceImpl.class);
    
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
                cacheService.removeFromCache(COUPON_LIST_KEY);
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
                cacheService.removeFromCache(COUPON_LIST_KEY);
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
        String key = COUPON_LIST_KEY + pageIndex + pageSize;
        pageIndex = pageIndex >= 0 ? pageIndex : 0;
        pageSize = pageSize > 0 ? pageSize : 10000;
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
        List<Coupon> res = null;
        ObjectMapper mapper = new ObjectMapper();
        if (pageIndex == 0) {
            if (!jedisKeys.exists(key)) {
                res = couponMapper.queryAll(rowIndex, pageSize);
                String jsonString = null;
                try {
                    jsonString = mapper.writeValueAsString(res);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                    throw new WareHouseManagerException("查询失败");
                }
                jedisStrings.set(key, jsonString);
            } else {
                String jsonString = jedisStrings.get(key);
                JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Coupon.class);
                try {
                    res = mapper.readValue(jsonString, javaType);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                    throw new WareHouseManagerException("查询失败");
                }
            }
        } else {
            try {
                res = couponMapper.queryAll(rowIndex,  pageSize);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询失败");
            } 
        }
        return res;
    }
}
