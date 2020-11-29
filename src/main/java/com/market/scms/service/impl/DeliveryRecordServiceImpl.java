package com.market.scms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.cache.JedisUtil;
import com.market.scms.entity.DeliveryRecord;
import com.market.scms.enums.DeliveryRefundStatusStateEnum;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.mapper.DeliveryRecordMapper;
import com.market.scms.service.CacheService;
import com.market.scms.service.DeliveryRecordService;
import com.market.scms.util.PageCalculator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/24 15:14
 */
@Service
public class DeliveryRecordServiceImpl implements DeliveryRecordService {
    
    @Resource
    private DeliveryRecordMapper deliveryRecordMapper;

    @Resource
    private JedisUtil.Keys jedisKeys;
    @Resource
    private JedisUtil.Strings jedisStrings;
    @Resource
    private CacheService cacheService;
    
    @Override
    public int insert(DeliveryRecord deliveryRecord) throws WareHouseManagerException {
        if (deliveryRecord != null && deliveryRecord.getDeliveryId() != null &&
                deliveryRecord.getDeliveryPaid() != null && 
                deliveryRecord.getDeliveryStatus() != null && deliveryRecord.getDeliveryLaunchedStaffId() != null &&
                deliveryRecord.getDeliveryCheckOutStatus() != null) {
            deliveryRecord.setDeliveryRefundStatus(DeliveryRefundStatusStateEnum.NO_REFUND.getState());
            deliveryRecord.setDeliveryCreateDate(new Date());
            try {
                int res = deliveryRecordMapper.insert(deliveryRecord);
                if (res == 0) {
                    throw new WareHouseManagerException("添加入库详情单失败");
                }
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("添加入库详情单失败");
            }
        } else {
            throw new WareHouseManagerException("信息不全，添加失败");
        }
    }

    @Override
    public int update(DeliveryRecord deliveryRecord) throws WareHouseManagerException {
        if (deliveryRecord != null && deliveryRecord.getDeliveryId() != null) {
            try {
                int res = deliveryRecordMapper.update(deliveryRecord);
                if (res == 0) {
                    throw new WareHouseManagerException("更新入库详情单失败");
                }
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("更新入库详情单失败");
            }
        } else {
            throw new WareHouseManagerException("信息不全，添加失败");
        }
    }

    @Override
    public DeliveryRecord queryByDeliveryId(String deliveryId) throws WareHouseManagerException {
        if (deliveryId != null) {
            try {
                DeliveryRecord deliveryRecord = deliveryRecordMapper.queryByDeliveryId(deliveryId);
                return deliveryRecord;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询失败");
            }
        } else {
            throw new WareHouseManagerException("信息不全，查询失败");
        }
    }

    @Override
    public List<DeliveryRecord> queryAll(int pageIndex, int pageSize) throws WareHouseManagerException {
        String key = DELIVERY_RECORD_LIST_KEY + pageIndex + pageSize;
        pageIndex = pageIndex >= 0 ? pageIndex : 0;
        pageSize = pageSize > 0 ? pageSize : 10000;
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
        List<DeliveryRecord> res = null;
        ObjectMapper mapper = new ObjectMapper();
        if (pageIndex == 0) {
            if (!jedisKeys.exists(key)) {
                res = deliveryRecordMapper.queryAll(rowIndex, pageSize);
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
                        .constructParametricType(ArrayList.class, DeliveryRecord.class);
                try {
                    res = mapper.readValue(jsonString, javaType);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    throw new WareHouseManagerException("查询出库详情单失败");
                }
            }
        } else {
            try {
                res = deliveryRecordMapper.queryAll(rowIndex, pageSize);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询出库详情单失败");
            }
        }
        return res;
    }

    @Override
    public List<DeliveryRecord> queryByCondition(DeliveryRecord deliveryRecordCondition, int pageIndex, int pageSize)
            throws WareHouseManagerException{
        if (deliveryRecordCondition != null) {
            try {
                int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
                List<DeliveryRecord> deliveryRecordList = deliveryRecordMapper
                        .queryByCondition(deliveryRecordCondition, rowIndex, pageSize);
                return deliveryRecordList;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询出库详情单失败");
            }
        } else {
            throw new WareHouseManagerException("查询出库详情单失败");
        }
    }
}
