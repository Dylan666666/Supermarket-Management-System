package com.market.scms.bean;

import java.io.Serializable;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/24 17:30
 */
public class DeliveryGoods implements Serializable {
    private static final long serialVersionUID = -938358623758058057L;
    private String goodsPicture;
    private Long deliveryStockGoodsId;
    private String goodsName;
    private String categoryName;
    private String goodsBrand;
    private Integer deliveryNum;
    private Double deliveryPrice;
    private String unitName;

    public String getGoodsPicture() {
        return goodsPicture;
    }

    public void setGoodsPicture(String goodsPicture) {
        this.goodsPicture = goodsPicture;
    }

    public Long getDeliveryStockGoodsId() {
        return deliveryStockGoodsId;
    }

    public void setDeliveryStockGoodsId(Long deliveryStockGoodsId) {
        this.deliveryStockGoodsId = deliveryStockGoodsId;
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

    public Integer getDeliveryNum() {
        return deliveryNum;
    }

    public void setDeliveryNum(Integer deliveryNum) {
        this.deliveryNum = deliveryNum;
    }

    public Double getDeliveryPrice() {
        return deliveryPrice;
    }
 
    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
