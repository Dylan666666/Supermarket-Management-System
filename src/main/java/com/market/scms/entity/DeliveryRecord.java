package com.market.scms.entity;

import java.util.Date;

/**
 * 出库详情单
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/22 9:46
 */
public class DeliveryRecord {
    private String deliveryId;
    private Double deliveryPaid;
    private Integer deliveryStatus;
    private Long deliveryLaunchedStaffId;
    private Long deliveryHandlerStaffId;
    private Double deliveryTotalPrice;
    private Integer deliveryCheckOutStatus;
    private Integer deliveryRefundStatus;
    private Date deliveryCreateDate;

    public String getDeliveryId() {
        return deliveryId;
    }

    public void setDeliveryId(String deliveryId) {
        this.deliveryId = deliveryId;
    }

    public Double getDeliveryPaid() {
        return deliveryPaid;
    }

    public void setDeliveryPaid(Double deliveryPaid) {
        this.deliveryPaid = deliveryPaid;
    }

    public Integer getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Integer deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Long getDeliveryLaunchedStaffId() {
        return deliveryLaunchedStaffId;
    }

    public void setDeliveryLaunchedStaffId(Long deliveryLaunchedStaffId) {
        this.deliveryLaunchedStaffId = deliveryLaunchedStaffId;
    }

    public Long getDeliveryHandlerStaffId() {
        return deliveryHandlerStaffId;
    }

    public void setDeliveryHandlerStaffId(Long deliveryHandlerStaffId) {
        this.deliveryHandlerStaffId = deliveryHandlerStaffId;
    }

    public Double getDeliveryTotalPrice() {
        return deliveryTotalPrice;
    }

    public void setDeliveryTotalPrice(Double deliveryTotalPrice) {
        this.deliveryTotalPrice = deliveryTotalPrice;
    }

    public Integer getDeliveryCheckOutStatus() {
        return deliveryCheckOutStatus;
    }

    public void setDeliveryCheckOutStatus(Integer deliveryCheckOutStatus) {
        this.deliveryCheckOutStatus = deliveryCheckOutStatus;
    }

    public Integer getDeliveryRefundStatus() {
        return deliveryRefundStatus;
    }

    public void setDeliveryRefundStatus(Integer deliveryRefundStatus) {
        this.deliveryRefundStatus = deliveryRefundStatus;
    }

    public Date getDeliveryCreateDate() {
        return deliveryCreateDate;
    }

    public void setDeliveryCreateDate(Date deliveryCreateDate) {
        this.deliveryCreateDate = deliveryCreateDate;
    }
}
