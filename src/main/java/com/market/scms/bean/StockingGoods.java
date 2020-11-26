package com.market.scms.bean;

/**
 * Goods+stock+stocking
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/26 22:09
 */
public class StockingGoods {
    private String goodsName;
    private Long goodsCategoryId;
    private String goodsBrand;
    private String goodsSpecifications;
    private String goodsPicture;
    private Long stockGoodsId;
    private Integer stockGoodsBatchNumber;
    private Integer stockInventory;
    private Long stocktakingId;
    private Integer stocktakingNum;
    private Integer stocktakingStatus;

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Long getGoodsCategoryId() {
        return goodsCategoryId;
    }

    public void setGoodsCategoryId(Long goodsCategoryId) {
        this.goodsCategoryId = goodsCategoryId;
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

    public Integer getStockGoodsBatchNumber() {
        return stockGoodsBatchNumber;
    }

    public void setStockGoodsBatchNumber(Integer stockGoodsBatchNumber) {
        this.stockGoodsBatchNumber = stockGoodsBatchNumber;
    }

    public Integer getStockInventory() {
        return stockInventory;
    }

    public void setStockInventory(Integer stockInventory) {
        this.stockInventory = stockInventory;
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
