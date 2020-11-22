package com.market.scms.entity;

import java.util.Date;

/**
 * 退货单
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/22 9:56
 */
public class RefundCustomerRecord {
    private String refundCustomerId;
    private String refundCustomerOrderId;
    private Date refundCustomerTime;
    private String refundCustomerReason;
    private Integer orderType;
    private Integer refundCustomerStatus;
    private Long refundStaffId;

    public String getRefundCustomerId() {
        return refundCustomerId;
    }

    public void setRefundCustomerId(String refundCustomerId) {
        this.refundCustomerId = refundCustomerId;
    }

    public String getRefundCustomerOrderId() {
        return refundCustomerOrderId;
    }

    public void setRefundCustomerOrderId(String refundCustomerOrderId) {
        this.refundCustomerOrderId = refundCustomerOrderId;
    }

    public Date getRefundCustomerTime() {
        return refundCustomerTime;
    }

    public void setRefundCustomerTime(Date refundCustomerTime) {
        this.refundCustomerTime = refundCustomerTime;
    }

    public String getRefundCustomerReason() {
        return refundCustomerReason;
    }

    public void setRefundCustomerReason(String refundCustomerReason) {
        this.refundCustomerReason = refundCustomerReason;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getRefundCustomerStatus() {
        return refundCustomerStatus;
    }

    public void setRefundCustomerStatus(Integer refundCustomerStatus) {
        this.refundCustomerStatus = refundCustomerStatus;
    }

    public Long getRefundStaffId() {
        return refundStaffId;
    }

    public void setRefundStaffId(Long refundStaffId) {
        this.refundStaffId = refundStaffId;
    }
}
