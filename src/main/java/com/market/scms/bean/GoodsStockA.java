package com.market.scms.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Goods和 Stock连接表A
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/22 10:26
 */
public class GoodsStockA implements Serializable {
    private static final long serialVersionUID = 2922945342247572036L;
    private Long goodsId;
    private String goodsName;
    private Integer goodsCategoryId;
    private String goodsBrand;
    private String goodsSpecifications;
    private String goodsPicture;
    private Long stockGoodsId;
    private Integer stockUnitId;
    private Integer stockGoodsBatchNumber;
    private Date stockGoodsProductionDate;
    private Integer stockGoodsShelfLife;
    private Double stockGoodsPrice;
    private Integer stockInventory;
    private String stockExportBillId;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

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

    public Integer getStockUnitId() {
        return stockUnitId;
    }

    public void setStockUnitId(Integer stockUnitId) {
        this.stockUnitId = stockUnitId;
    }

    public Integer getStockGoodsBatchNumber() {
        return stockGoodsBatchNumber;
    }

    public void setStockGoodsBatchNumber(Integer stockGoodsBatchNumber) {
        this.stockGoodsBatchNumber = stockGoodsBatchNumber;
    }

    public Date getStockGoodsProductionDate() {
        return stockGoodsProductionDate;
    }

    public void setStockGoodsProductionDate(Date stockGoodsProductionDate) {
        this.stockGoodsProductionDate = stockGoodsProductionDate;
    }

    public Integer getStockGoodsShelfLife() {
        return stockGoodsShelfLife;
    }

    public void setStockGoodsShelfLife(Integer stockGoodsShelfLife) {
        this.stockGoodsShelfLife = stockGoodsShelfLife;
    }

    public Double getStockGoodsPrice() {
        return stockGoodsPrice;
    }

    public void setStockGoodsPrice(Double stockGoodsPrice) {
        this.stockGoodsPrice = stockGoodsPrice;
    }

    public Integer getStockInventory() {
        return stockInventory;
    }

    public void setStockInventory(Integer stockInventory) {
        this.stockInventory = stockInventory;
    }

    public String getStockExportBillId() {
        return stockExportBillId;
    }

    public void setStockExportBillId(String stockExportBillId) {
        this.stockExportBillId = stockExportBillId;
    }
}
