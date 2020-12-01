package com.market.scms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.cache.JedisUtil;
import com.market.scms.entity.Stocktaking;
import com.market.scms.enums.StocktakingStatusEnum;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.mapper.StocktakingMapper;
import com.market.scms.service.CacheService;
import com.market.scms.service.StocktakingService;
import com.market.scms.util.PageCalculator;
import com.market.scms.util.StocktakingIdCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/26 20:03
 */
@Service
public class StocktakingServiceImpl implements StocktakingService {
    
    @Resource
    private StocktakingMapper stocktakingMapper;

    @Resource
    private JedisUtil.Keys jedisKeys;
    @Resource
    private JedisUtil.Strings jedisStrings;
    @Resource
    private CacheService cacheService;
    
    @Override
    public int insert(Stocktaking stocktaking) throws WareHouseManagerException {
        if (stocktaking != null && stocktaking.getStocktakingStockGoodsId() != null &&
        stocktaking.getStockNum() != null) {
            try {
                int res = stocktakingMapper.insert(stocktaking);
                if (res == 0) {
                    throw new WareHouseManagerException("添加盘点单失败");
                }
                cacheService.removeFromCache(STOCKTAKING_LIST_KEY);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("添加失败");
            }
        } else {
            throw new WareHouseManagerException("不具备添加条件，添加失败");
        }
    }

    @Override
    public int update(Stocktaking stocktaking) throws WareHouseManagerException {
        if (stocktaking != null && stocktaking.getStocktakingStockGoodsId() != null &&
                stocktaking.getStocktakingId() != null) {
            try {
                if (stocktaking.getStocktakingStatus().equals(StocktakingStatusEnum.SECOND.getState())) {
                    stocktaking.setStocktakingTime(new Date());
                }
                int res = stocktakingMapper.update(stocktaking);
                if (res == 0) {
                    throw new WareHouseManagerException("更新盘点单失败");
                }
                cacheService.removeFromCache(STOCKTAKING_LIST_KEY);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("更新失败");
            }
        } else {
            throw new WareHouseManagerException("不具备更新条件，更新失败");
        }
    }

    @Override
    public Stocktaking queryById(Long stockTakingId, Long stockTakingStockGoodsId) throws WareHouseManagerException {
        if (stockTakingId != null && stockTakingStockGoodsId != null) {
            try {
                Stocktaking stocktaking = stocktakingMapper.queryById(stockTakingId, stockTakingStockGoodsId);
                return stocktaking;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询失败");
            }
        } else {
            throw new WareHouseManagerException("不具备查询条件，查询失败");
        }
    }

    @Override
    public List<Stocktaking> queryAll(int pageIndex, int pageSize) throws WareHouseManagerException {
        String key = STOCKTAKING_LIST_KEY + pageIndex + pageSize;
        pageIndex = pageIndex >= 0 ? pageIndex : 0;
        pageSize = pageSize > 0 ? pageSize : 10000;
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
        List<Stocktaking> res = null;
        ObjectMapper mapper = new ObjectMapper();
        if (pageIndex == 0) {
            if (!jedisKeys.exists(key)) {
                res = stocktakingMapper.queryAll(rowIndex, pageSize);
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
                JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Stocktaking.class);
                try {
                    res = mapper.readValue(jsonString, javaType);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    throw new WareHouseManagerException("查询失败");
                }
            }
        } else {
            try {
                res = stocktakingMapper.queryAll(rowIndex,  pageSize);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询失败");
            }
        }
        return res;
    }

    @Override
    public List<Stocktaking> queryByCondition(Stocktaking stocktakingCondition, int pageIndex, int pageSize)
            throws WareHouseManagerException {
        if (stocktakingCondition != null) {
            int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
            List<Stocktaking> stocktakingList = stocktakingMapper.queryByCondition(stocktakingCondition, rowIndex, pageSize);
            return stocktakingList;
        } else {
            throw new WareHouseManagerException("查询失败");
        }
    }

    @Override
    public List<Stocktaking> queryByStocktakingId(Long stocktakingId) throws WareHouseManagerException {
        if (stocktakingId > 0) {
            try {
                List<Stocktaking> stocktakingList = stocktakingMapper.queryByStocktakingId(stocktakingId);
                return stocktakingList;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("查询失败");
            }
        } else {
            throw new WareHouseManagerException("不具备查询条件，查询失败");
        }
    }

    @Override
    public int getCount(String dateFormat) throws WareHouseManagerException {
        try {
            return getCount(dateFormat);
        } catch (WareHouseManagerException e) {
            throw new WareHouseManagerException("不具备获取Id条件，注册失败");
        }
    }
}
