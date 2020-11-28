package com.market.scms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.cache.JedisUtil;
import com.market.scms.entity.RefundCustomerRecord;
import com.market.scms.exceptions.SaleException;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.mapper.RefundCustomerRecordMapper;
import com.market.scms.service.CacheService;
import com.market.scms.service.RefundCustomerRecordService;
import com.market.scms.util.PageCalculator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/28 13:35
 */
@Service
public class RefundCustomerRecordServiceImpl implements RefundCustomerRecordService {
    
    @Resource
    private RefundCustomerRecordMapper refundCustomerRecordMapper;

    @Resource
    private JedisUtil.Keys jedisKeys;
    @Resource
    private JedisUtil.Strings jedisStrings;
    @Resource
    private CacheService cacheService;
    
    @Override
    public int insert(RefundCustomerRecord refundCustomerRecord) throws SaleException {
        if (refundCustomerRecord != null && refundCustomerRecord.getRefundCustomerId() != null &&
        refundCustomerRecord.getOrderType() != null) {
            try {
                int res = refundCustomerRecordMapper.insert(refundCustomerRecord);
                if (res == 0) {
                    throw new SaleException("添加失败");
                }
                cacheService.removeFromCache(REFUND_CUSTOMER_RECORD_LIST_KEY);
                return res;
            } catch (SaleException e) {
                throw new SaleException("添加失败");
            }
        } else {
            throw new SaleException("添加失败");
        }
    }

    @Override
    public int update(RefundCustomerRecord refundCustomerRecord) throws SaleException  {
        if (refundCustomerRecord != null && refundCustomerRecord.getRefundCustomerId() != null) {
            try {
                int res = refundCustomerRecordMapper.update(refundCustomerRecord);
                if (res == 0) {
                    throw new SaleException("更改失败");
                }
                cacheService.removeFromCache(REFUND_CUSTOMER_RECORD_LIST_KEY);
                return res;
            } catch (SaleException e) {
                throw new SaleException("更改失败");
            }
        } else {
            throw new SaleException("更新失败");
        }
    }

    @Override
    public RefundCustomerRecord queryByRefundCustomerId(String refundCustomerId) throws SaleException  {
        if (refundCustomerId != null) {
            try {
                RefundCustomerRecord refundCustomerRecord = refundCustomerRecordMapper
                        .queryByRefundCustomerId(refundCustomerId);
                return refundCustomerRecord;
            } catch (SaleException e) {
                throw new SaleException("查询失败");
            }
        } else {
            throw new SaleException("查询失败");
        }
    }

    @Override
    public List<RefundCustomerRecord> queryAll(int pageIndex, int pageSize) throws SaleException  {
        String key = REFUND_CUSTOMER_RECORD_LIST_KEY + pageIndex + pageSize;
        pageIndex = pageIndex >= 0 ? pageIndex : 0;
        pageSize = pageSize > 0 ? pageSize : 10000;
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
        List<RefundCustomerRecord> res = null;
        ObjectMapper mapper = new ObjectMapper();
        if (pageIndex == 0) {
            if (!jedisKeys.exists(key)) {
                res = refundCustomerRecordMapper.queryAll(rowIndex, pageSize);
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
                        .constructParametricType(ArrayList.class, RefundCustomerRecord.class);
                try {
                    res = mapper.readValue(jsonString, javaType);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    throw new WareHouseManagerException("查询失败");
                }
            }
        } else {
            try {
                res = refundCustomerRecordMapper.queryAll(rowIndex,  pageSize);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询失败");
            }
        }
        return res;
    }

    @Override
    public List<RefundCustomerRecord> queryByCondition(RefundCustomerRecord refundCustomerRecordCondition, 
                                                       int pageIndex, int pageSize) throws SaleException {
        if (refundCustomerRecordCondition != null) {
            try {
                int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
                List<RefundCustomerRecord> refundCustomerRecordList = 
                        refundCustomerRecordMapper.queryByCondition(refundCustomerRecordCondition, rowIndex, pageSize);
                return refundCustomerRecordList;
            } catch (SaleException e) {
                throw new SaleException("查询失败");
            }
        } else {
            throw new SaleException("查询失败");
        }
    }
}
