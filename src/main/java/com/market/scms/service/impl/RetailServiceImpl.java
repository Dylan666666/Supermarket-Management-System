package com.market.scms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.cache.JedisUtil;
import com.market.scms.entity.Delivery;
import com.market.scms.entity.Retail;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.mapper.RetailMapper;
import com.market.scms.service.CacheService;
import com.market.scms.service.RetailService;
import com.market.scms.util.PageCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/25 11:07
 */
@Service
public class RetailServiceImpl implements RetailService {
    
    @Resource
    private RetailMapper retailMapper;

    @Resource
    private JedisUtil.Keys jedisKeys;
    @Resource
    private JedisUtil.Strings jedisStrings;
    @Resource
    private CacheService cacheService;

    private static Logger logger = LoggerFactory.getLogger(RetailServiceImpl.class);
    
    @Override
    public int insert(Retail retail) throws WareHouseManagerException {
        if (retail != null && retail.getRetailId() != null && retail.getRetailStockGoodsId() != null) {
            try {
                int res = retailMapper.insert(retail);
                if (res == 0) {
                    throw new WareHouseManagerException("添加订单失败");
                }
                cacheService.removeFromCache(RETAIL_LIST_KEY);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("添加订单失败");
            }
        } else {
            throw new WareHouseManagerException("添加订单失败");
        }
    }

    @Override
    public int update(Retail retail) throws WareHouseManagerException {
        if (retail != null && retail.getRetailId() != null && retail.getRetailStockGoodsId() != null
        && retail.getRetailNum() >= 0 && retail.getRetailPrice() >= 0) {
            try {
                int res = retailMapper.update(retail);
                if (res == 0) {
                    throw new WareHouseManagerException("更新订单失败");
                }
                cacheService.removeFromCache(RETAIL_LIST_KEY);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("更新订单失败");
            }
        } else {
            throw new WareHouseManagerException("更新订单失败");
        }
    }

    @Override
    public List<Retail> queryByRetailId(String retailId) throws WareHouseManagerException {
        if (retailId != null) {
            String key = RETAIL_LIST_KEY + "retailId" + retailId;
            List<Retail> res = null;
            ObjectMapper mapper = new ObjectMapper();
            if (!jedisKeys.exists(key)) {
                res = retailMapper.queryByRetailId(retailId);
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
                JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Retail.class);
                try {
                    res = mapper.readValue(jsonString, javaType);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    logger.error(e.getMessage());
                    throw new WareHouseManagerException("查询失败");
                }
            }
            return res;
        } else {
            throw new WareHouseManagerException("传入信息为空，查询订单失败");
        }
    }

    @Override
    public Retail queryByGoodsId(String retailId, Long retailStockGoodsId) throws WareHouseManagerException {
        if (retailId != null && retailStockGoodsId > 0) {
            try {
                Retail retail = retailMapper.queryByGoodsId(retailId, retailStockGoodsId);
                return retail;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询订单失败");
            }
        } else {
            throw new WareHouseManagerException("查询订单失败");
        }
    }

    @Override
    public List<Retail> queryAll(int pageIndex, int pageSize) throws WareHouseManagerException {
        String key = RETAIL_LIST_KEY + pageIndex + pageSize;
        pageIndex = pageIndex >= 0 ? pageIndex : 0;
        pageSize = pageSize > 0 ? pageSize : 10000;
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
        List<Retail> res = null;
        ObjectMapper mapper = new ObjectMapper();
        if (pageIndex == 0) {
            if (!jedisKeys.exists(key)) {
                res = retailMapper.queryAll(rowIndex, pageSize);
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
                JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Retail.class);
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
                res = retailMapper.queryAll(rowIndex,  pageSize);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询失败");
            }
        }
        return res;
    }
}
