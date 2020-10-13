package com.market.scms.entity;

import java.util.Date;

/**
 * 入库单
 * @Author: Mr_OO
 * @Date: 2020/10/7 15:45
 */
public class GodownEntry {
    private Long godownEntryId;
    private Long godownEntryGoodsId;
    private Long godownEntrySupplierId;
    private Long godownOrderId;
    private Date godownEntryDate;
    private Double godownEntryGoodsPrice;
    private Double godownEntryPaid;
    private Integer godownEntryNum;
    /**
     *入库状态
     */
    private Integer godownEntryStatus;

    public Long getGodownEntryId() {
        return godownEntryId;
    }

    public void setGodownEntryId(Long godownEntryId) {
        this.godownEntryId = godownEntryId;
    }

    public Long getGodownEntryGoodsId() {
        return godownEntryGoodsId;
    }

    public void setGodownEntryGoodsId(Long godownEntryGoodsId) {
        this.godownEntryGoodsId = godownEntryGoodsId;
    }

    public Long getGodownEntrySupplierId() {
        return godownEntrySupplierId;
    }

    public void setGodownEntrySupplierId(Long godownEntrySupplierId) {
        this.godownEntrySupplierId = godownEntrySupplierId;
    }

    public Long getGodownOrderId() {
        return godownOrderId;
    }

    public void setGodownOrderId(Long godownOrderId) {
        this.godownOrderId = godownOrderId;
    }

    public Date getGodownEntryDate() {
        return godownEntryDate;
    }

    public void setGodownEntryDate(Date godownEntryDate) {
        this.godownEntryDate = godownEntryDate;
    }

    public Double getGodownEntryGoodsPrice() {
        return godownEntryGoodsPrice;
    }

    public void setGodownEntryGoodsPrice(Double godownEntryGoodsPrice) {
        this.godownEntryGoodsPrice = godownEntryGoodsPrice;
    }

    public Double getGodownEntryPaid() {
        return godownEntryPaid;
    }

    public void setGodownEntryPaid(Double godownEntryPaid) {
        this.godownEntryPaid = godownEntryPaid;
    }

    public Integer getGodownEntryNum() {
        return godownEntryNum;
    }

    public void setGodownEntryNum(Integer godownEntryNum) {
        this.godownEntryNum = godownEntryNum;
    }

    public Integer getGodownEntryStatus() {
        return godownEntryStatus;
    }

    public void setGodownEntryStatus(Integer godownEntryStatus) {
        this.godownEntryStatus = godownEntryStatus;
    }
}
