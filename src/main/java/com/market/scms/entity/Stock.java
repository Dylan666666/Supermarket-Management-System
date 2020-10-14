package com.market.scms.entity;

/**
 * 库存表
 * @Author: Mr_OO
 * @Date: 2020/10/7 15:54
 */
public class Stock {
    private Long stocksId;
    private Long stockGoodsId;
    private Integer stockGoodsNum;

    public Long getStocksId() {
        return stocksId;
    }

    public void setStocksId(Long stocksId) {
        this.stocksId = stocksId;
    }

    public Long getStockGoodsId() {
        return stockGoodsId;
    }

    public void setStockGoodsId(Long stockGoodsId) {
        this.stockGoodsId = stockGoodsId;
    }

    public Integer getStockGoodsNum() {
        return stockGoodsNum;
    }

    public void setStockGoodsNum(Integer stockGoodsNum) {
        this.stockGoodsNum = stockGoodsNum;
    }
}
