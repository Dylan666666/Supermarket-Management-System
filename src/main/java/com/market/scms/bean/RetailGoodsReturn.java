package com.market.scms.bean;

import java.io.Serializable;

/**
 * @Author: Mr_OO
 * @Date: 2020/12/4 10:17
 */
public class RetailGoodsReturn implements Serializable {
    private static final long serialVersionUID = -3586175307819731971L;
    private Long retailStockGoodsId;
    private String goodsPicture;
    private String goodsName;
    private String goodsBrand;
    private String unitName;
    private String categoryName;
    private Integer retailNum;
    private Double retailPrice;
    private Integer refundCustomerNum;
    private Double refundCustomerPrice;

    public Long getRetailStockGoodsId() {
        return retailStockGoodsId;
    }

    public void setRetailStockGoodsId(Long retailStockGoodsId) {
        this.retailStockGoodsId = retailStockGoodsId;
    }

    public String getGoodsPicture() {
        return goodsPicture;
    }

    public void setGoodsPicture(String goodsPicture) {
        this.goodsPicture = goodsPicture;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsBrand() {
        return goodsBrand;
    }

    public void setGoodsBrand(String goodsBrand) {
        this.goodsBrand = goodsBrand;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getRetailNum() {
        return retailNum;
    }

    public void setRetailNum(Integer retailNum) {
        this.retailNum = retailNum;
    }

    public Double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(Double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public Integer getRefundCustomerNum() {
        return refundCustomerNum;
    }

    public void setRefundCustomerNum(Integer refundCustomerNum) {
        this.refundCustomerNum = refundCustomerNum;
    }

    public Double getRefundCustomerPrice() {
        return refundCustomerPrice;
    }

    public void setRefundCustomerPrice(Double refundCustomerPrice) {
        this.refundCustomerPrice = refundCustomerPrice;
    }
}
