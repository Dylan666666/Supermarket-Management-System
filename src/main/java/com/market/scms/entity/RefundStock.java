package com.market.scms.entity;

/**
 * 退货单
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/22 10:14
 */
public class RefundStock {
    private String refundCustomerId;
    private Long refundCustomerStockGoodsId;
    private Integer refundCustomerNum;
    private Double refundCustomerPrice;

    public String getRefundCustomerId() {
        return refundCustomerId;
    }

    public void setRefundCustomerId(String refundCustomerId) {
        this.refundCustomerId = refundCustomerId;
    }

    public Long getRefundCustomerStockGoodsId() {
        return refundCustomerStockGoodsId;
    }

    public void setRefundCustomerStockGoodsId(Long refundCustomerStockGoodsId) {
        this.refundCustomerStockGoodsId = refundCustomerStockGoodsId;
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
