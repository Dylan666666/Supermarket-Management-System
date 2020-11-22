package com.market.scms.entity;

/**
 * 商品出库单
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/22 9:51
 */
public class Delivery {
    private String deliveryId;
    private Long deliveryStockGoodsId;
    private Double deliveryPrice;
    private Integer deliveryNum;

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Long getDeliveryStockGoodsId() {
        return deliveryStockGoodsId;
    }

    public void setDeliveryStockGoodsId(Long deliveryStockGoodsId) {
        this.deliveryStockGoodsId = deliveryStockGoodsId;
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
}
