package com.market.scms.entity;

import java.util.Date;

/**
 * 盘点单
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/22 10:08
 */
public class Stocktaking {
    private Long stocktakingId;
    private Long stocktakingStockGoodsId;
    private Integer stockNum;
    private Integer stocktakingNum;
    private Long stocktakingStaffId;
    private Integer stocktakingStatus;
    private String stocktakingRemarks;
    private Date stocktakingTime;
    private Integer stocktakingProfitLossStatus;
    private Double stocktakingPrice;

    public Long getStocktakingId() {
        return stocktakingId;
    }

    public void setStocktakingId(Long stocktakingId) {
        this.stocktakingId = stocktakingId;
    }

    public Long getStocktakingStockGoodsId() {
        return stocktakingStockGoodsId;
    }

    public void setStocktakingStockGoodsId(Long stocktakingStockGoodsId) {
        this.stocktakingStockGoodsId = stocktakingStockGoodsId;
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    public Integer getStocktakingNum() {
        return stocktakingNum;
    }

    public void setStocktakingNum(Integer stocktakingNum) {
        this.stocktakingNum = stocktakingNum;
    }

    public Long getStocktakingStaffId() {
        return stocktakingStaffId;
    }

    public void setStocktakingStaffId(Long stocktakingStaffId) {
        this.stocktakingStaffId = stocktakingStaffId;
    }

    public Integer getStocktakingStatus() {
        return stocktakingStatus;
    }

    public void setStocktakingStatus(Integer stocktakingStatus) {
        this.stocktakingStatus = stocktakingStatus;
    }

    public String getStocktakingRemarks() {
        return stocktakingRemarks;
    }

    public void setStocktakingRemarks(String stocktakingRemarks) {
        this.stocktakingRemarks = stocktakingRemarks;
    }

    public Date getStocktakingTime() {
        return stocktakingTime;
    }

    public void setStocktakingTime(Date stocktakingTime) {
        this.stocktakingTime = stocktakingTime;
    }

    public Integer getStocktakingProfitLossStatus() {
        return stocktakingProfitLossStatus;
    }

    public void setStocktakingProfitLossStatus(Integer stocktakingProfitLossStatus) {
        this.stocktakingProfitLossStatus = stocktakingProfitLossStatus;
    }

    public Double getStocktakingPrice() {
        return stocktakingPrice;
    }

    public void setStocktakingPrice(Double stocktakingPrice) {
        this.stocktakingPrice = stocktakingPrice;
    }
}
