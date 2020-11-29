package com.market.scms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.cache.JedisUtil;
import com.market.scms.entity.RefundRetailRecord;
import com.market.scms.exceptions.SaleException;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.mapper.RefundRetailRecordMapper;
import com.market.scms.service.CacheService;
import com.market.scms.service.RefundRetailRecordService;
import com.market.scms.util.PageCalculator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/28 15:22
 */
@Service
public class RefundRetailRecordServiceImpl implements RefundRetailRecordService {
    
    @Resource
    private RefundRetailRecordMapper refundRetailRecordMapper;

    @Resource
    private JedisUtil.Keys jedisKeys;
    @Resource
    private JedisUtil.Strings jedisStrings;
    @Resource
    private CacheService cacheService;
    
    @Override
    public int insert(RefundRetailRecord refundRetailRecord) throws SaleException {
        if (refundRetailRecord != null && refundRetailRecord.getRefundRetailId() != null) {
            try {
                int res = refundRetailRecordMapper.insert(refundRetailRecord);
                if (res == 0) {
                    throw new SaleException("添加失败");
                }
                cacheService.removeFromCache(REFUND_RETAIL_RECORD_LIST_KEY);
                return res;
            } catch (SaleException e) {
                throw new SaleException("添加失败");
            }
        } else {
            throw new SaleException("添加失败");
        }
    }

    @Override
    public int update(RefundRetailRecord refundRetailRecord) throws SaleException {
        if (refundRetailRecord != null && refundRetailRecord.getRefundRetailId() != null) {
            try {
                int res = refundRetailRecordMapper.update(refundRetailRecord);
                if (res == 0) {
                    throw new SaleException("更改失败");
                }
                cacheService.removeFromCache(REFUND_RETAIL_RECORD_LIST_KEY);
                return res;
            } catch (SaleException e) {
                throw new SaleException("更改失败");
            }
        } else {
            throw new SaleException("更改失败");
        }
    }

    @Override
    public RefundRetailRecord queryByRefundRetailId(String refundRetailId) throws SaleException {
        if (refundRetailId != null) {
            try {
                RefundRetailRecord refundRetailRecord = refundRetailRecordMapper.queryByRefundRetailId(refundRetailId);
                return refundRetailRecord;
            } catch (SaleException e) {
                throw new SaleException("查询失败");
            }
        } else {
            throw new SaleException("查询失败");
        }
    }

    @Override
    public List<RefundRetailRecord> queryAll(int pageIndex, int pageSize) throws SaleException {
        String key = REFUND_RETAIL_RECORD_LIST_KEY + pageIndex + pageSize;
        pageIndex = pageIndex >= 0 ? pageIndex : 0;
        pageSize = pageSize > 0 ? pageSize : 10000;
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
        List<RefundRetailRecord> res = null;
        ObjectMapper mapper = new ObjectMapper();
        if (pageIndex == 0) {
            if (!jedisKeys.exists(key)) {
                res = refundRetailRecordMapper.queryAll(rowIndex, pageSize);
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
                        .constructParametricType(ArrayList.class, RefundRetailRecord.class);
                try {
                    res = mapper.readValue(jsonString, javaType);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    throw new WareHouseManagerException("查询失败");
                }
            }
        } else {
            try {
                res = refundRetailRecordMapper.queryAll(rowIndex,  pageSize);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询失败");
            }
        }
        return res;
    }

    @Override
    public List<RefundRetailRecord> queryByCondition(
            RefundRetailRecord refundRetailRecordCondition, int pageIndex, int pageSize) 
            throws SaleException {
        if (refundRetailRecordCondition != null) {
            try {
                List<RefundRetailRecord> refundRetailRecordList = refundRetailRecordMapper
                        .queryByCondition(refundRetailRecordCondition, pageIndex, pageSize);
                return refundRetailRecordList;
            } catch (SaleException e) {
                throw new SaleException("查询失败");
            }
        } else {
            throw new SaleException("查询失败");
        }
    }
}
