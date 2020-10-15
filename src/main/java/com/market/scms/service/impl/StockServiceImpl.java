package com.market.scms.service.impl;

import com.market.scms.dao.StockDao;
import com.market.scms.entity.Stock;
import com.market.scms.exceptions.StockException;
import com.market.scms.service.StockService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/15 8:52
 */
@Service
public class StockServiceImpl implements StockService {
    
    @Resource
    private StockDao stockDao;

    /**
     * 通过库存编号查询库存表
     *
     * @param stockId
     * @return
     */
    @Override
    public Stock queryStockById(Long stockId) throws StockException {
        if (stockId > 0) {
            try {
                Stock stock = stockDao.queryStockById(stockId);
                return stock;
            } catch (StockException e) {
                throw new StockException("查询失败");
            }
        } else {
            throw new StockException("传入信息有错");
        }
    }

    /**
     * 通过商品单号查询库存表
     *
     * @param stockGoodsId
     * @return
     */
    @Override
    public Stock queryStockByGoodsId(Long stockGoodsId) throws StockException {
        if (stockGoodsId > 0) {
            try {
                Stock stock = stockDao.queryStockByGoodsId(stockGoodsId);
                return stock;
            } catch (StockException e) {
                throw new StockException("查询失败");
            }
        } else {
            throw new StockException("传入信息有错");
        }
    }

    /**
     * 添加新库存表单
     *
     * @param stock
     * @return
     */
    @Override
    public int insertStock(Stock stock) throws StockException {
        if (stock != null) {
            try {
                int res = stockDao.insertStock(stock);
                if (res == 0) {
                    throw new StockException("添加失败");
                }
                return res;
            } catch (StockException e) {
                throw new StockException("添加失败");
            }
        } else {
            throw new StockException("传入信息有错");
        }
    }

    /**
     * 更改库存表单信息
     *
     * @param stock
     * @return
     */
    @Override
    public int updateStock(Stock stock) throws StockException {
        if (stock != null && stock.getStockGoodsNum() != null && stock.getStockGoodsNum() >= 0) {
            try {
                int res = stockDao.updateStock(stock);
                if (res == 0) {
                    throw new StockException("更改失败");
                }
                return res;
            } catch (StockException e) {
                throw new StockException("添加失败");
            }
        } else {
            throw new StockException("传入信息有错");
        }
    }
}
