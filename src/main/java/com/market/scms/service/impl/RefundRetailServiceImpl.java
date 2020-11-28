package com.market.scms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.cache.JedisUtil;
import com.market.scms.entity.RefundRetail;
import com.market.scms.exceptions.SaleException;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.mapper.RefundRetailMapper;
import com.market.scms.service.CacheService;
import com.market.scms.service.RefundRetailService;
import com.market.scms.util.PageCalculator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/28 14:54
 */
@Service
public class RefundRetailServiceImpl implements RefundRetailService {
    
    @Resource
    private RefundRetailMapper refundRetailMapper;

    @Resource
    private JedisUtil.Keys jedisKeys;
    @Resource
    private JedisUtil.Strings jedisStrings;
    @Resource
    private CacheService cacheService;
    
    @Override
    public int insert(RefundRetail refundRetail) throws SaleException {
        if (refundRetail != null && refundRetail.getRefundRetailId() != null && 
                refundRetail.getRetailStockGoodsId() != null && refundRetail.getRefundCustomerId() != null) {
            try {
                int res = refundRetailMapper.insert(refundRetail);
                if (res == 0) {
                    throw new SaleException("添加失败");
                }
                cacheService.removeFromCache(REFUND_RETAIL_LIST_KEY);
                return res;
            } catch (SaleException e) {
                throw new SaleException("添加失败");
            }
        } else {
            throw new SaleException("添加失败");
        }
    }

    @Override
    public int update(RefundRetail refundRetail) throws SaleException {
        if (refundRetail != null && refundRetail.getRefundRetailId() != null &&
                refundRetail.getRetailStockGoodsId() != null && refundRetail.getRefundCustomerId() != null) {
            try {
                int res = refundRetailMapper.update(refundRetail);
                if (res == 0) {
                    throw new SaleException("更新失败");
                }
                cacheService.removeFromCache(REFUND_RETAIL_LIST_KEY);
                return res;
            } catch (SaleException e) {
                throw new SaleException("更新失败");
            }
        } else {
            throw new SaleException("更新失败");
        }
    }

    @Override
    public List<RefundRetail> queryByRefundRetailId(String refundRetailId) throws SaleException {
        if (refundRetailId != null) {
            try {
                List<RefundRetail> refundRetailList = refundRetailMapper.queryByRefundRetailId(refundRetailId);
                return refundRetailList;
            } catch (SaleException e) {
                throw new SaleException("查询失败");
            }
        } else {
            throw new SaleException("查询失败");
        }
    }

    @Override
    public List<RefundRetail> queryByStockGoodsId(Long retailStockGoodsId) throws SaleException {
        if (retailStockGoodsId > 0) {
            try {
                List<RefundRetail> refundRetailList = refundRetailMapper.queryByStockGoodsId(retailStockGoodsId);
                return refundRetailList;
            } catch (SaleException e) {
                throw new SaleException("查询失败");
            }
        } else {
            throw new SaleException("查询失败");
        }
    }

    @Override
    public List<RefundRetail> queryByRefundCustomerId(String refundCustomerId) throws SaleException {
        if (refundCustomerId != null) {
            try {
                List<RefundRetail> refundRetailList = refundRetailMapper.queryByRefundCustomerId(refundCustomerId);
                return refundRetailList;
            } catch (SaleException e) {
                throw new SaleException("查询失败");
            }
        } else {
            throw new SaleException("查询失败");
        }
    }

    @Override
    public RefundRetail queryByTribeId(String refundRetailId, Long retailStockGoodsId, String refundCustomerId)
            throws SaleException{
        if (refundCustomerId != null && retailStockGoodsId > 0 && refundCustomerId != null) {
            try {
                RefundRetail refundRetail = refundRetailMapper
                        .queryByTribeId(refundRetailId, retailStockGoodsId, refundCustomerId);
                return refundRetail;
            } catch (SaleException e) {
                throw new SaleException("查询失败");
            }
        } else {
            throw new SaleException("查询失败");
        }
    }

    @Override
    public List<RefundRetail> queryAll(int pageIndex, int pageSize) throws SaleException {
        String key = REFUND_RETAIL_LIST_KEY + pageIndex + pageSize;
        pageIndex = pageIndex >= 0 ? pageIndex : 0;
        pageSize = pageSize > 0 ? pageSize : 10000;
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
        List<RefundRetail> res = null;
        ObjectMapper mapper = new ObjectMapper();
        if (pageIndex == 0) {
            if (!jedisKeys.exists(key)) {
                res = refundRetailMapper.queryAll(rowIndex, pageSize);
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
                        .constructParametricType(ArrayList.class, RefundRetail.class);
                try {
                    res = mapper.readValue(jsonString, javaType);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    throw new WareHouseManagerException("查询失败");
                }
            }
        } else {
            try {
                res = refundRetailMapper.queryAll(rowIndex,  pageSize);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询失败");
            }
        }
        return res;
    }
}
