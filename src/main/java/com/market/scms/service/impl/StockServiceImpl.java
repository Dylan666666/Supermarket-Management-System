package com.market.scms.service.impl;

import com.market.scms.entity.Stock;
import com.market.scms.exceptions.WareHouseManagerException;
import com.market.scms.mapper.StockMapper;
import com.market.scms.service.StockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 22:05
 */
@Service
public class StockServiceImpl implements StockService {
    
    @Resource
    private StockMapper stockMapper;
    
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
        if (stock != null && stock.getStockGoodsId() != null && stock.getStockInventory() != null &&
                stock.getStockInventory() >= 0) {
            try {
                int res = stockMapper.update(stock);
                if (res == 0) {
                    throw new WareHouseManagerException("更改库存失败");
                }
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
    public List<Stock> queryAll() throws WareHouseManagerException {
        try {
            List<Stock> list = stockMapper.queryAll();
            return list;
        } catch (WareHouseManagerException e) {
            throw new WareHouseManagerException("查询库存失败");
        }
    }
}
