package com.market.scms.entity;

/**
 * 商品退货联系表
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/22 9:59
 */
public class RefundCustomer {
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
