package com.market.scms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.cache.JedisUtil;
import com.market.scms.dto.ImageHolder;
import com.market.scms.entity.Goods;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.mapper.GoodsMapper;
import com.market.scms.service.CacheService;
import com.market.scms.service.GoodsService;
import com.market.scms.util.ImageUtil;
import com.market.scms.util.PageCalculator;
import com.market.scms.util.PathUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 12:37
 */
@Service
public class GoodsServiceImpl implements GoodsService {
    
    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private JedisUtil.Keys jedisKeys;
    @Resource
    private JedisUtil.Strings jedisStrings;
    @Resource
    private CacheService cacheService;
    
    @Override
    public int insertGoods(Goods goods, ImageHolder thumbnail) throws WareHouseManagerException {
        isNull(goods);
        if (goods.getGoodsName() != null && goods.getGoodsBrand() != null && 
                goods.getGoodsCategoryId() != null && goods.getGoodsId() != null &&
                goods.getGoodsSpecifications() != null) {
            try {
                if (thumbnail != null) {
                    addThumbnail(goods, thumbnail);
                }
                int res = goodsMapper.insertGoods(goods);
                if (res == 0) {
                    throw new WareHouseManagerException("创建商品失败");
                }
                cacheService.removeFromCache(GOODS_LIST_KEY);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("创建商品失败");
            }   
        } else {
            throw new WareHouseManagerException("缺少必要信息");
        }
    }

    @Override
    public int updateGoods(Goods goods, ImageHolder thumbnail) throws WareHouseManagerException {
        isNull(goods);
        if (goods.getGoodsId() != null) {
            try {
                if (thumbnail != null) {
                    Goods cur = new Goods();
                    cur.setGoodsId(goods.getGoodsId());
                    cur = goodsMapper.queryByCondition(cur, 0, 100).get(0);
                    if (cur != null && cur.getGoodsPicture() != null) {
                        ImageUtil.deleteFileOrPath(cur.getGoodsPicture());
                    }
                    addThumbnail(goods, thumbnail);
                }
                int res = goodsMapper.updateGoods(goods);
                if (res == 0) {
                    throw new WareHouseManagerException("更新商品失败");
                }
                cacheService.removeFromCache(GOODS_LIST_KEY);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("更新商品失败");
            }
        } else {
            throw new WareHouseManagerException("缺少必要信息");
        }
    }

    @Override
    public List<Goods> queryAll() throws WareHouseManagerException {
        String key = GOODS_LIST_KEY;
        ObjectMapper mapper = new ObjectMapper();
        List<Goods> res = null;
        if (!jedisKeys.exists(key)) {
            res = goodsMapper.queryAll();
            String jsonString = null;
            try {
                jsonString = mapper.writeValueAsString(res);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new WareHouseManagerException("查询商品失败");
            }
            jedisStrings.set(key, jsonString);
        } else {
            String jsonString = jedisStrings.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Goods.class);
            try {
                res = mapper.readValue(jsonString, javaType);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                throw new WareHouseManagerException("查询商品失败");
            }
        }
        return res;
    }

    @Override
    public List<Goods> queryByCondition(Goods goodsCondition, int pageIndex, int pageSize) 
            throws WareHouseManagerException {
        isNull(goodsCondition);
        if (pageIndex != -1000 && pageSize != -1000) {
            try {
                int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
                List<Goods> res = goodsMapper.queryByCondition(goodsCondition, rowIndex, pageSize);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询失败");
            }   
        } else {
            throw new WareHouseManagerException("查询失败");
        }
    }

    @Override
    public int deleteGoods(Long goodsId) throws WareHouseManagerException {
        try {
            if (goodsId != null) {
                int res = goodsMapper.deleteGoods(goodsId);
                if (res == 0) {
                    throw new WareHouseManagerException("删除失败");
                }
                cacheService.removeFromCache(GOODS_LIST_KEY);
                return res;
            } else {
                throw new WareHouseManagerException("不具备删除条件");
            }
        } catch (WareHouseManagerException e) {
            throw new WareHouseManagerException("删除失败");
        }
    }

    @Override
    public Goods queryById(Long goodsId) throws WareHouseManagerException {
        if (goodsId != null) {
            Goods goods = goodsMapper.queryById(goodsId);
            return goods;
        } else {
            throw new WareHouseManagerException("传入信息为空");
        }
    }

    private void isNull(Goods goods) throws WareHouseManagerException {
        if (goods == null) {
            throw new WareHouseManagerException("传入信息为空");
        }
    }

    /**
     * 添加缩略图
     * 
     * @param goods
     * @param thumbnail
     */
    private void addThumbnail(Goods goods, ImageHolder thumbnail) {
        String dest = PathUtil.getGoodsImagePath(goods.getGoodsCategoryId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        goods.setGoodsPicture(thumbnailAddr);
    }
}
