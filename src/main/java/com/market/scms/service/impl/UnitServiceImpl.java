package com.market.scms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.cache.JedisUtil;
import com.market.scms.entity.Unit;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.mapper.UnitMapper;
import com.market.scms.service.CacheService;
import com.market.scms.service.UnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 13:49
 */
@Service
public class UnitServiceImpl implements UnitService {
    
    @Resource
    private UnitMapper unitMapper;

    @Resource
    private JedisUtil.Keys jedisKeys;
    @Resource
    private JedisUtil.Strings jedisStrings;
    @Resource
    private CacheService cacheService;

    private static Logger logger = LoggerFactory.getLogger(UnitServiceImpl.class);
    
    @Override
    public List<Unit> queryAll() throws WareHouseManagerException {
        String key = UNIT_LIST_KEY;
        ObjectMapper mapper = new ObjectMapper();
        List<Unit> res = null;
        if (!jedisKeys.exists(key)) {
            res = unitMapper.queryAll();
            String jsonString = null;
            try {
                jsonString = mapper.writeValueAsString(res);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new WareHouseManagerException("查询商品单位失败");
            }
            jedisStrings.set(key, jsonString);
        } else {
            String jsonString = jedisStrings.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Unit.class);
            try {
                res = mapper.readValue(jsonString, javaType);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new WareHouseManagerException("查询商品单位失败");
            }
        }
        return res;
    }

    @Override
    public int insert(Unit unit) throws WareHouseManagerException {
        try {
            int res = unitMapper.insert(unit);
            if (res == 0) {
                throw new WareHouseManagerException("销售单位插入失败");
            }
            cacheService.removeFromCache(UNIT_LIST_KEY);
            return res;
        } catch (WareHouseManagerException e) {
            throw new WareHouseManagerException("销售单位插入失败");
        }
    }

    @Override
    public Unit queryById(int unitId) throws WareHouseManagerException {
        if (unitId > 0) {
            Unit unit = unitMapper.queryById(unitId);
            return unit;
        } else {
            throw new WareHouseManagerException("查询单位失败");
        }
    }
}
