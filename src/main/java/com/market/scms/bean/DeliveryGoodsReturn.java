package com.market.scms.bean;

import java.io.Serializable;

/**
 * delivery + stock + goods + goods_category + unit + refund_customer 连接
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/27 20:38
 */
public class DeliveryGoodsReturn implements Serializable {

    private static final long serialVersionUID = 3996099126278877141L;
    private String goodsPicture;
    private Long deliveryStockGoodsId;
    private String goodsName;
    private String categoryName;
    private String goodsBrand;
    private Double deliveryPrice;
    private Integer deliveryNum;
    private String unitName;
    private Integer refundCustomerNum;
    private Double refundCustomerPrice;

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

    public Double getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(Double deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public Integer getDeliveryNum() {
        return deliveryNum;
    }

    public void setDeliveryNum(Integer deliveryNum) {
        this.deliveryNum = deliveryNum;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
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
