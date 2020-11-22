package com.market.scms.entity;

/**
 * 退货单详情表
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/22 10:19
 */
public class RefundRetail {
    private String refundRetailId;
    private Long retailStockGoodsId;
    private String refundCustomerId;
    private Integer retailNum;
    private Double retailPrice;

    public String getRefundRetailId() {
        return refundRetailId;
    }

    public void setRefundRetailId(String refundRetailId) {
        this.refundRetailId = refundRetailId;
    }

    public Long getRetailStockGoodsId() {
        return retailStockGoodsId;
    }

    public void setRetailStockGoodsId(Long retailStockGoodsId) {
        this.retailStockGoodsId = retailStockGoodsId;
    }

    public String getRefundCustomerId() {
        return refundCustomerId;
    }

    public void setRefundCustomerId(String refundCustomerId) {
        this.refundCustomerId = refundCustomerId;
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
}
