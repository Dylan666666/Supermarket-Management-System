package com.market.scms.entity;

import java.util.Date;

/**
 * 盘点记录表
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/22 10:05
 */
public class StocktakingRecord {
    private Long stocktakingId;
    private Long stocktakingLaunchedStaffId;
    private Long stocktakingSubmitStaffId;
    private Double stocktakingProfitLossPrice;
    private Integer stocktakingAllStatus;
    private Date stocktakingLaunchedDate;
    private Date stocktakingCommitDate;

    public Long getStocktakingId() {
        return stocktakingId;
    }

    public void setStocktakingId(Long stocktakingId) {
        this.stocktakingId = stocktakingId;
    }

    public Long getStocktakingLaunchedStaffId() {
        return stocktakingLaunchedStaffId;
    }

    public void setStocktakingLaunchedStaffId(Long stocktakingLaunchedStaffId) {
        this.stocktakingLaunchedStaffId = stocktakingLaunchedStaffId;
    }

    public Long getStocktakingSubmitStaffId() {
        return stocktakingSubmitStaffId;
    }

    public void setStocktakingSubmitStaffId(Long stocktakingSubmitStaffId) {
        this.stocktakingSubmitStaffId = stocktakingSubmitStaffId;
    }

    public Double getStocktakingProfitLossPrice() {
        return stocktakingProfitLossPrice;
    }

    public void setStocktakingProfitLossPrice(Double stocktakingProfitLossPrice) {
        this.stocktakingProfitLossPrice = stocktakingProfitLossPrice;
    }

    public Integer getStocktakingAllStatus() {
        return stocktakingAllStatus;
    }

    public void setStocktakingAllStatus(Integer stocktakingAllStatus) {
        this.stocktakingAllStatus = stocktakingAllStatus;
    }

    public Date getStocktakingLaunchedDate() {
        return stocktakingLaunchedDate;
    }

    public void setStocktakingLaunchedDate(Date stocktakingLaunchedDate) {
        this.stocktakingLaunchedDate = stocktakingLaunchedDate;
    }

    public Date getStocktakingCommitDate() {
        return stocktakingCommitDate;
    }

    public void setStocktakingCommitDate(Date stocktakingCommitDate) {
        this.stocktakingCommitDate = stocktakingCommitDate;
    }
}
