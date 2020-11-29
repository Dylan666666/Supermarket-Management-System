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
            try {
                GoodsCategory goodsCategory = goodsCategoryMapper.queryById(categoryId);
                return goodsCategory;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询产品类别失败");
            }
        } else {
            throw new WareHouseManagerException("查询产品类别失败");
        }
    }

    @Override
    public int update(GoodsCategory goodsCategory) throws WareHouseManagerException {
        if (goodsCategory != null) {
            try {
                int res = goodsCategoryMapper.update(goodsCategory);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("更新产品类别失败");
            }
        } else {
            throw new WareHouseManagerException("更新产品类别失败");
        }
    }
}
