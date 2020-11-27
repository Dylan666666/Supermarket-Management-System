package com.market.scms.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.market.scms.cache.JedisUtil;
import com.market.scms.entity.Stock;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.mapper.StockMapper;
import com.market.scms.service.CacheService;
import com.market.scms.service.StockService;
import com.market.scms.util.PageCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 22:05
 */
@Service
public class StockServiceImpl implements StockService {
    
    @Resource
    private StockMapper stockMapper;

    @Resource
    private JedisUtil.Keys jedisKeys;
    @Resource
    private JedisUtil.Strings jedisStrings;
    @Resource
    private CacheService cacheService;

    private static Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);
    
    @Override
    public int insert(Stock stock) throws WareHouseManagerException {
        if (stock != null && stock.getGoodsStockId() != null && stock.getStockUnitId() != null && 
                stock.getStockGoodsBatchNumber() != null && stock.getStockGoodsProductionDate() != null &&
                stock.getStockGoodsShelfLife() != null && stock.getStockGoodsPrice() != null && 
                stock.getStockInventory() != null && stock.getStockExportBillId() != null) {
            //目前赋默认仓库编号
            stock.setStockId(1);
            try {
                int res = stockMapper.insert(stock);
                if (res == 0) {
                    throw new WareHouseManagerException("添加库存失败");
                }
                cacheService.removeFromCache(STOCK_LIST_KEY);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("添加库存失败");
            }
        } else {
            throw new WareHouseManagerException("插入信息不全，添加库存失败");
        }
    }

    @Override
    public int update(Stock stock) throws WareHouseManagerException {
        if (stock != null && stock.getStockGoodsId() != null ) {
            if (stock.getStockInventory() != null && stock.getStockInventory() < 0) {
                throw new WareHouseManagerException("传入信息有误，更改库存失败");
            }
            if (stock.getStockGoodsPrice() != null && stock.getStockGoodsPrice() < 0) {
                throw new WareHouseManagerException("传入信息有误，更改库存失败");
            }
            try {
                int res = stockMapper.update(stock);
                if (res == 0) {
                    throw new WareHouseManagerException("更改库存失败");
                }
                cacheService.removeFromCache(STOCK_LIST_KEY);
                return res;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("更改库存失败");
            }
        } else {
            throw new WareHouseManagerException("传入信息有误，更改库存失败");
        }
    }

    @Override
    public Stock queryByGoodsId(Long goodsStockId) throws WareHouseManagerException {
        if (goodsStockId > 0) {
            try {
                Stock stock = stockMapper.queryByGoodsId(goodsStockId);
                return stock;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("传入信息有误，查询库存失败");
            }
        } else {
            throw new WareHouseManagerException("传入信息有误，查询库存失败");
        }
    }

    @Override
    public Stock queryById(Long stockGoodsId) throws WareHouseManagerException {
        if (stockGoodsId > 0) {
            try {
                Stock stock = stockMapper.queryById(stockGoodsId);
                return stock;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("传入信息有误，查询库存失败");
            }
        } else {
            throw new WareHouseManagerException("传入信息有误，查询库存失败");
        }
    }

    @Override
    public Stock queryByExportBillId(String stockExportBillId) throws WareHouseManagerException {
        if (stockExportBillId != null) {
            try {
                Stock stock = stockMapper.queryByExportBillId(stockExportBillId);
                return stock;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("传入信息有误，查询库存失败");
            }
        } else {
            throw new WareHouseManagerException("传入信息有误，查询库存失败");
        }
    }

    @Override
    public List<Stock> queryByCondition(Stock stockCondition) throws WareHouseManagerException {
        if (stockCondition != null) {
            try {
                List<Stock> list = stockMapper.queryByCondition(stockCondition);
                return list;
            } catch (WareHouseManagerException e) {
                throw new WareHouseManagerException("传入信息有误，查询库存失败");
            }
        } else {
            throw new WareHouseManagerException("传入信息有误，查询库存失败");
        }
    }

    @Override
    public List<Stock> queryAll(int pageIndex, int pageSize) throws WareHouseManagerException {
        String key = STOCK_LIST_KEY + pageIndex + pageSize;
        pageIndex = pageIndex >= 0 ? pageIndex : 0;
        pageSize = pageSize > 0 ? pageSize : 10000;
        int rowIndex = PageCalculator.calculatorRowIndex(pageIndex, pageSize);
        ObjectMapper mapper = new ObjectMapper();
        List<Stock> res = null;
        if (!jedisKeys.exists(key)) {
            res = stockMapper.queryAll(rowIndex, pageSize);
            String jsonString = null;
            try {
                jsonString = mapper.writeValueAsString(res);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new WareHouseManagerException("查询库存失败");
            }
            jedisStrings.set(key, jsonString);
        } else {
            String jsonString = jedisStrings.get(key);
            JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Stock.class);
            try {
                res = mapper.readValue(jsonString, javaType);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                logger.error(e.getMessage());
                throw new WareHouseManagerException("查询库存失败");
            }
        }
        return res;
    }
}
