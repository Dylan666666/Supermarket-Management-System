package com.market.scms.bean;

import java.io.Serializable;

/**
 * 零售出库单商品列表(delivery+stock+goods+goods_category+unit+连接)
 * @Author: Mr_OO
 * @Date: 2020/12/4 10:07
 */
public class RetailGoods implements Serializable {
    private static final long serialVersionUID = -3288601382361404027L;
    private Long retailStockGoodsId;
    private Integer retailNum;
    private Double retailPrice;
    private String goodsPicture;
    private String goodsName;
    private String categoryName;
    private String goodsBrand;
    private String unitName;

    public Long getRetailStockGoodsId() {
        return retailStockGoodsId;
    }

    public void setRetailStockGoodsId(Long retailStockGoodsId) {
        this.retailStockGoodsId = retailStockGoodsId;
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

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
