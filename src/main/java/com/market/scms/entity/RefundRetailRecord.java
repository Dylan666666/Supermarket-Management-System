package com.market.scms.entity;

import java.util.Date;

/**
 * 退货记录表
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/22 10:17
 */
public class RefundRetailRecord {
    private String refundRetailId;
    private Date retailTime;
    private Long retailCollectionStaffId;
    private Double retailTotalPrice;

    public String getRefundRetailId() {
        return refundRetailId;
    }

    public void setRefundRetailId(String refundRetailId) {
        this.refundRetailId = refundRetailId;
    }

    public Date getRetailTime() {
        return retailTime;
    }

    public void setRetailTime(Date retailTime) {
        this.retailTime = retailTime;
    }

    public Long getRetailCollectionStaffId() {
        return retailCollectionStaffId;
    }

    public void setRetailCollectionStaffId(Long retailCollectionStaffId) {
        this.retailCollectionStaffId = retailCollectionStaffId;
    }

    public Double getRetailTotalPrice() {
        return retailTotalPrice;
    }

    public void setRetailTotalPrice(Double retailTotalPrice) {
        this.retailTotalPrice = retailTotalPrice;
    }
}
