package com.market.scms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.cache.JedisUtil;
import com.market.scms.entity.GoodsCategory;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.mapper.GoodsCategoryMapper;
import com.market.scms.service.CacheService;
import com.market.scms.service.GoodsCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 16:27
 */
@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {
    
    @Resource
    private GoodsCategoryMapper goodsCategoryMapper;

    @Resource
    private JedisUtil.Keys jedisKeys;
    @Resource
    private JedisUtil.Strings jedisStrings;
    @Resource
    private CacheService cacheService;

    private static Logger logger = LoggerFactory.getLogger(GoodsCategoryServiceImpl.class);
    
    @Override
    public List<GoodsCategory> queryAll() throws WareHouseManagerException {
        String key = GOODS_CATEGORY_KEY;
        ObjectMapper mapper = new ObjectMapper();
        List<GoodsCategory> res = null;
        if (!jedisKeys.exists(key)) {
            res = goodsCategoryMapper.queryAll();
            String jsonString = null;
            try {
                jsonString = mapper.writeValueAsString(res);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new WareHouseManagerException("查询产品类别失败");
            }
            jedisStrings.set(key, jsonString);
        } else {
            String jsonString = jedisStrings.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, GoodsCategory.class);
            try {
                res = mapper.readValue(jsonString, javaType);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new WareHouseManagerException("查询产品类别失败");
            }
        }
        return res;
    }

    @Override
    public int insert(GoodsCategory goodsCategory) throws WareHouseManagerException {
        if (goodsCategory == null && goodsCategory.getCategoryId() != null &&
        goodsCategory.getCategoryName() != null) {
            throw new WareHouseManagerException("传入信息不完整");
        } else {
            try {
                int res = goodsCategoryMapper.insert(goodsCategory);
                if (res == 0) {
                    throw new WareHouseManagerException("添加产品类别失败");
                }
                cacheService.removeFromCache(GOODS_CATEGORY_KEY);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("添加产品类别失败");
            }   
        }
    }

    @Override
    public GoodsCategory queryById(int categoryId) throws WareHouseManagerException {
        if (categoryId > 0) {
            GoodsCategory goodsCategory = goodsCategoryMapper.queryById(categoryId);
            return goodsCategory;
        } else {
            throw new WareHouseManagerException("查询产品类别失败");
        }
    }
}
