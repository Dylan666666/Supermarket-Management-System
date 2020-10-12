package com.market.scms.entity;

import java.util.Date;

/**
 * 订货表
 * @Author: Mr_OO
 * @Date: 2020/10/7 15:31
 */
public class OrderForm {
    private Long orderId;
    private String goodsName;
    private String goodsCategory;
    private String goodsDetailedDescription;
    private Integer goodsNum;
    /**
     * 
     * 订单状态
     */
    private Integer orderStatus;
    private Date orderTime;

    public String getGoodsDetailedDescription() {
        return goodsDetailedDescription;
    }

    public void setGoodsDetailedDescription(String goodsDetailedDescription) {
        this.goodsDetailedDescription = goodsDetailedDescription;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsCategory() {
        return goodsCategory;
    }

    public void setGoodsCategory(String goodsCategory) {
        this.goodsCategory = goodsCategory;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }
}
