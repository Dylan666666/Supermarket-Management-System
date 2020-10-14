package com.market.scms.entity;

/**
 * 库存表
 * @Author: Mr_OO
 * @Date: 2020/10/7 15:54
 */
public class Stock {
    private Long stockId;
    private Long stockGoodsId;
    private Integer stockGoodsNum;

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
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
