package com.market.scms.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * stocktaking+stock+goods+goods_category +supermarketStaff连接
 * 
 * @Author: Mr_OO
 * @Date: 2020/12/15 11:20
 */
public class StocktakingGoods implements Serializable {

    private static final long serialVersionUID = 6652733279289188643L;

    private String goodsName;
    private String categoryName;
    private String goodsBrand;
    private String goodsSpecifications;
    private String goodsPicture;
    private Long stocktakingId;
    private Long stocktakingStockGoodsId;
    private Integer stockNum;
    private Integer stocktakingNum;
    private String staffName;
    private Integer stocktakingStatus;
    private String stocktakingRemarks;
    private Date stocktakingTime;
    private Integer stocktakingProfitLossStatus;
    private Double stocktakingPrice;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getGoodsBrand() {
        return goodsBrand;
    }

    public void setGoodsBrand(String goodsBrand) {
        this.goodsBrand = goodsBrand;
    }

    public String getGoodsSpecifications() {
        return goodsSpecifications;
    }

    public void setGoodsSpecifications(String goodsSpecifications) {
        this.goodsSpecifications = goodsSpecifications;
    }

    public String getGoodsPicture() {
        return goodsPicture;
    }

    public void setGoodsPicture(String goodsPicture) {
        this.goodsPicture = goodsPicture;
    }

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

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
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
