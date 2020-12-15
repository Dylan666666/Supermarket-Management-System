package com.market.scms.bean;

import java.util.Date;

/**
 * Goods+stock+stocking
 * 
 * @Author: Mr_OO
 * @Date: 2020/12/15 12:26
 */
public class StockingGoods {
    private String goodsName;
    private Integer goodsCategoryId;
    private String categoryName;
    private String goodsBrand;
    private String goodsSpecifications;
    private String goodsPicture;
    private Long stockGoodsId;
    private Long stockGoodsBatchNumber;
    private Integer stockNum;
    private Long stocktakingId;
    private Integer stocktakingNum;
    private Integer stocktakingStatus;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsCategoryId() {
        return goodsCategoryId;
    }

    public void setGoodsCategoryId(Integer goodsCategoryId) {
        this.goodsCategoryId = goodsCategoryId;
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

    public Long getStockGoodsId() {
        return stockGoodsId;
    }

    public void setStockGoodsId(Long stockGoodsId) {
        this.stockGoodsId = stockGoodsId;
    }

    public Long getStockGoodsBatchNumber() {
        return stockGoodsBatchNumber;
    }

    public void setStockGoodsBatchNumber(Long stockGoodsBatchNumber) {
        this.stockGoodsBatchNumber = stockGoodsBatchNumber;
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    public Long getStocktakingId() {
        return stocktakingId;
    }

    public void setStocktakingId(Long stocktakingId) {
        this.stocktakingId = stocktakingId;
    }

    public Integer getStocktakingNum() {
        return stocktakingNum;
    }

    public void setStocktakingNum(Integer stocktakingNum) {
        this.stocktakingNum = stocktakingNum;
    }

    public Integer getStocktakingStatus() {
        return stocktakingStatus;
    }

    public void setStocktakingStatus(Integer stocktakingStatus) {
        this.stocktakingStatus = stocktakingStatus;
    }
}
