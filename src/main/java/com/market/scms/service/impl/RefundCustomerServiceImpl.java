package com.market.scms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.cache.JedisUtil;
import com.market.scms.entity.RefundCustomer;
import com.market.scms.exceptions.SaleException;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.mapper.RefundCustomerMapper;
import com.market.scms.service.CacheService;
import com.market.scms.service.RefundCustomerService;
import com.market.scms.util.PageCalculator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/28 13:06
 */
@Service
public class RefundCustomerServiceImpl implements RefundCustomerService {

    @Resource
    private RefundCustomerMapper refundCustomerMapper;

    @Resource
    private JedisUtil.Keys jedisKeys;
    @Resource
    private JedisUtil.Strings jedisStrings;
    @Resource
    private CacheService cacheService;
    
    @Override
    public int insert(RefundCustomer refundCustomer) throws SaleException {
        if (refundCustomer != null && refundCustomer.getRefundCustomerId() != null &&
                refundCustomer.getRefundCustomerStockGoodsId() != null) {
            int res = refundCustomerMapper.insert(refundCustomer);
            if (res == 0) {
                throw new SaleException("添加失败");
            }
            cacheService.removeFromCache(REFUND_CUSTOMER_LIST_KEY);
            return res;
        } else {
            throw new SaleException("添加失败");
        }
    }

    @Override
    public int update(RefundCustomer refundCustomer) throws SaleException {
        if (refundCustomer != null && refundCustomer.getRefundCustomerId() != null &&
                refundCustomer.getRefundCustomerStockGoodsId() != null) {
            int res = refundCustomerMapper.update(refundCustomer);
            if (res == 0) {
                throw new SaleException("更新失败");
            }
            cacheService.removeFromCache(REFUND_CUSTOMER_LIST_KEY);
            return res;
        } else {
            throw new SaleException("更新失败");
        }
    }

    @Override
    public List<RefundCustomer> queryByRefundId(String refundCustomerId) throws SaleException {
        if (refundCustomerId != null) {
            List<RefundCustomer> res = refundCustomerMapper.queryByRefundId(refundCustomerId);
            return res;
        } else {
            throw new SaleException("查询失败");
        }
    }

    @Override
    public RefundCustomer queryByDoubleId(String refundCustomerId, Long refundCustomerStockGoodsId)
            throws SaleException{
        if (refundCustomerId != null && refundCustomerStockGoodsId != null) {
            RefundCustomer refundCustomer = refundCustomerMapper
                    .queryByDoubleId(refundCustomerId, refundCustomerStockGoodsId);
            return refundCustomer;
        } else {
            throw new SaleException("查询失败");
        }
    }

    @Override
    public List<RefundCustomer> queryAll(int pageIndex, int pageSize) throws SaleException {
        String key = REFUND_CUSTOMER_LIST_KEY + pageIndex + pageSize;
        pageIndex = pageIndex >= 0 ? pageIndex : 0;
        pageSize = pageSize > 0 ? pageSize : 10000;
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
        List<RefundCustomer> res = null;
        ObjectMapper mapper = new ObjectMapper();
        if (pageIndex == 0) {
            if (!jedisKeys.exists(key)) {
                res = refundCustomerMapper.queryAll(rowIndex, pageSize);
                String jsonString = null;
                try {
                    jsonString = mapper.writeValueAsString(res);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    throw new WareHouseManagerException("查询失败");
                }
                jedisStrings.set(key, jsonString);
            } else {
                String jsonString = jedisStrings.get(key);
                JavaType javaType = mapper.getTypeFactory()
                        .constructParametricType(ArrayList.class, RefundCustomer.class);
                try {
                    res = mapper.readValue(jsonString, javaType);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    throw new WareHouseManagerException("查询失败");
                }
            }
        } else {
            try {
                res = refundCustomerMapper.queryAll(rowIndex,  pageSize);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询失败");
            }
        }
        return res;
    }
}
