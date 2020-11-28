package com.market.scms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.cache.JedisUtil;
import com.market.scms.entity.RefundRetail;
import com.market.scms.entity.RefundStock;
import com.market.scms.exceptions.SaleException;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.mapper.RefundStockMapper;
import com.market.scms.service.CacheService;
import com.market.scms.service.RefundStockService;
import com.market.scms.util.PageCalculator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/28 16:02
 */
@Service
public class RefundStockServiceImpl implements RefundStockService {
    
    @Resource
    private RefundStockMapper refundStockMapper;

    @Resource
    private JedisUtil.Keys jedisKeys;
    @Resource
    private JedisUtil.Strings jedisStrings;
    @Resource
    private CacheService cacheService;
    
    @Override
    public int insert(RefundStock refundStock) throws SaleException {
        if (refundStock != null && refundStock.getRefundCustomerId() != null &&
                refundStock.getRefundCustomerStockGoodsId() != null) {
            try {
                int res = refundStockMapper.insert(refundStock);
                if (res == 0) {
                    throw new SaleException("添加失败");
                }
                cacheService.removeFromCache(REFUND_STOCK_LIST_KEY);
                return res;
            } catch (SaleException e) {
                throw new SaleException("添加失败");
            }
        } else {
            throw new SaleException("添加失败");
        }
    }

    @Override
    public int update(RefundStock refundStock) throws SaleException {
        if (refundStock != null && refundStock.getRefundCustomerId() != null &&
                refundStock.getRefundCustomerStockGoodsId() != null) {
            try {
                int res = refundStockMapper.update(refundStock);
                if (res == 0) {
                    throw new SaleException("更改失败");
                }
                cacheService.removeFromCache(REFUND_STOCK_LIST_KEY);
                return res;
            } catch (SaleException e) {
                throw new SaleException("更改失败");
            }
        } else {
            throw new SaleException("更改失败");
        }
    }

    @Override
    public RefundStock queryByDoubleId(String refundCustomerId, Long refundCustomerStockGoodsId) throws SaleException {
        if (refundCustomerId != null && refundCustomerStockGoodsId > 0) {
            try {
                RefundStock refundStock = refundStockMapper
                        .queryByDoubleId(refundCustomerId, refundCustomerStockGoodsId);
                return refundStock;
            } catch (SaleException e) {
                throw new SaleException("查询失败");
            }
        } else {
            throw new SaleException("查询失败");
        }
    }

    @Override
    public List<RefundStock> queryByRefundCustomerId(String refundCustomerId) throws SaleException {
        if (refundCustomerId != null) {
            try {
                List<RefundStock> refundStockList = refundStockMapper.queryByRefundCustomerId(refundCustomerId);
                return refundStockList;
            } catch (SaleException e) {
                throw new SaleException("查询失败");
            }
        } else {
            throw new SaleException("查询失败");
        }
    }

    @Override
    public List<RefundStock> queryByRefundCustomerStockGoodsId(Long refundCustomerStockGoodsId) throws SaleException {
        if (refundCustomerStockGoodsId > 0) {
            try {
                List<RefundStock> refundStockList = refundStockMapper
                        .queryByRefundCustomerStockGoodsId(refundCustomerStockGoodsId);
                return refundStockList;
            } catch (SaleException e) {
                throw new SaleException("查询失败");
            }
        } else {
            throw new SaleException("查询失败");
        }
    }

    @Override
    public List<RefundStock> queryByCondition(RefundStock refundStockCondition, int pageIndex, int pageSize)
            throws SaleException {
        if (refundStockCondition != null) {
            try {
                List<RefundStock> refundStockList = refundStockMapper
                        .queryByCondition(refundStockCondition, pageIndex, pageSize);
                return refundStockList;
            } catch (SaleException e) {
                throw new SaleException("查询失败");
            }
        } else {
            throw new SaleException("查询失败");
        }
    }

    @Override
    public List<RefundStock> queryAll(int pageIndex, int pageSize) throws SaleException {
        String key = REFUND_STOCK_LIST_KEY + pageIndex + pageSize;
        pageIndex = pageIndex >= 0 ? pageIndex : 0;
        pageSize = pageSize > 0 ? pageSize : 10000;
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
        List<RefundStock> res = null;
        ObjectMapper mapper = new ObjectMapper();
        if (pageIndex == 0) {
            if (!jedisKeys.exists(key)) {
                res = refundStockMapper.queryAll(rowIndex, pageSize);
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
                        .constructParametricType(ArrayList.class, RefundStock.class);
                try {
                    res = mapper.readValue(jsonString, javaType);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    throw new WareHouseManagerException("查询失败");
                }
            }
        } else {
            try {
                res = refundStockMapper.queryAll(rowIndex,  pageSize);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询失败");
            }
        }
        return res;
    }
}
