package com.market.scms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.cache.JedisUtil;
import com.market.scms.entity.Delivery;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.mapper.DeliveryMapper;
import com.market.scms.service.CacheService;
import com.market.scms.service.DeliveryService;
import com.market.scms.util.PageCalculator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/23 21:34
 */
@Service
public class DeliveryServiceImpl implements DeliveryService {
    
    @Resource
    private DeliveryMapper deliveryMapper;

    @Resource
    private JedisUtil.Keys jedisKeys;
    @Resource
    private JedisUtil.Strings jedisStrings;
    @Resource
    private CacheService cacheService;
    
    @Override
    public int insert(Delivery delivery) throws WareHouseManagerException {
        if (delivery != null && delivery.getDeliveryId() != null && delivery.getDeliveryStockGoodsId() != null) {
            try {
                int res = deliveryMapper.insert(delivery);
                if (res == 0) {
                    throw new WareHouseManagerException("添加出库单失败");
                }
                cacheService.removeFromCache(DELIVERY_LIST_KEY);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("添加出库单失败");
            }
        } else {
            throw new WareHouseManagerException("传入信息为空，添加失败");
        }
    }

    @Override
    public int update(Delivery delivery) throws WareHouseManagerException {
        if (delivery != null && delivery.getDeliveryId() != null && delivery.getDeliveryStockGoodsId() != null
        && delivery.getDeliveryPrice() >= 0 && delivery.getDeliveryNum() >= 0) {
            try {
                int res = deliveryMapper.update(delivery);
                if (res == 0) {
                    throw new WareHouseManagerException("更改出库单失败");
                }
                cacheService.removeFromCache(DELIVERY_LIST_KEY);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("更改出库单失败");
            }
        } else {
            throw new WareHouseManagerException("传入信息为空，更改失败");
        }
    }

    @Override
    public List<Delivery> queryByDeliveryId(String deliveryId) throws WareHouseManagerException {
        if (deliveryId == null) {
             throw new WareHouseManagerException("传入信息为空，查询失败");
        }
        String key = DELIVERY_LIST_KEY + "deliveryId" + deliveryId;
        List<Delivery> res = null;
        ObjectMapper mapper = new ObjectMapper();
        if (!jedisKeys.exists(key)) {
            res = deliveryMapper.queryByDeliveryId(deliveryId);
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
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Delivery.class);
            try {
                res = mapper.readValue(jsonString, javaType);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new WareHouseManagerException("查询失败");
            }
        }
        return res;
    }

    @Override
    public Delivery queryByGoodsId(String deliveryId, Long goodsId) throws WareHouseManagerException {
        if (deliveryId != null && goodsId > 0) {
            try {
                Delivery delivery = deliveryMapper.queryByGoodsId(deliveryId, goodsId);
                return delivery;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询失败");
            }
        } else {
            throw new WareHouseManagerException("传入信息为空，查询失败");
        }
    }

    @Override
    public List<Delivery> queryAll(int pageIndex, int pageSize) throws WareHouseManagerException {
        String key = DELIVERY_LIST_KEY + pageIndex + pageSize;
        pageIndex = pageIndex >= 0 ? pageIndex : 0;
        pageSize = pageSize > 0 ? pageSize : 10000;
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
        List<Delivery> res = null;
        ObjectMapper mapper = new ObjectMapper();
        if (pageIndex == 0) {
            if (!jedisKeys.exists(key)) {
                res = deliveryMapper.queryAll(rowIndex, pageSize);
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
                JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Delivery.class);
                try {
                    res = mapper.readValue(jsonString, javaType);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    throw new WareHouseManagerException("查询失败");
                }
            }
        } else {
            try {
                res = deliveryMapper.queryAll(rowIndex,  pageSize);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询失败");
            }
        }
        return res;
    }
}
