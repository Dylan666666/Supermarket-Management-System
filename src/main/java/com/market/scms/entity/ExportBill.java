package com.market.scms.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 入库单
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/18 10:14
 */
public class ExportBill implements Serializable {
    private static final long serialVersionUID = 6408294412260535161L;
    private String exportBillId;
    private Long exportBillCouponId;
    private Long exportBillSupplierId;
    private String exportBillGoodsBatchNumber;
    private Date exportBillProductionDate;
    private Integer exportBillShelfLife;
    private Double exportBillPrice;
    private Integer exportBillStatus;
    private Double exportBillPaid;
    private Date exportBillTime;
    private Integer exportConfirmStaffId;
    private Integer exportSubmitStaffId;
    private String exportBillMark;

    public String getExportBillMark() {
        return exportBillMark;
    }

    public void setExportBillMark(String exportBillMark) {
        this.exportBillMark = exportBillMark;
    }

    public String getExportBillId() {
        return exportBillId;
    }

    public void setExportBillId(String exportBillId) {
        this.exportBillId = exportBillId;
    }

    public Long getExportBillCouponId() {
        return exportBillCouponId;
    }

    public void setExportBillCouponId(Long exportBillCouponId) {
        this.exportBillCouponId = exportBillCouponId;
    }

    public Long getExportBillSupplierId() {
        return exportBillSupplierId;
    }

    public void setExportBillSupplierId(Long exportBillSupplierId) {
        this.exportBillSupplierId = exportBillSupplierId;
    }

    public String getExportBillGoodsBatchNumber() {
        return exportBillGoodsBatchNumber;
    }

    public void setExportBillGoodsBatchNumber(String exportBillGoodsBatchNumber) {
        this.exportBillGoodsBatchNumber = exportBillGoodsBatchNumber;
    }

    public Date getExportBillProductionDate() {
        return exportBillProductionDate;
    }

    public void setExportBillProductionDate(Date exportBillProductionDate) {
        this.exportBillProductionDate = exportBillProductionDate;
    }

    public Integer getExportBillShelfLife() {
        return exportBillShelfLife;
    }

    public void setExportBillShelfLife(Integer exportBillShelfLife) {
        this.exportBillShelfLife = exportBillShelfLife;
    }

    public Double getExportBillPrice() {
        return exportBillPrice;
    }

    public void setExportBillPrice(Double exportBillPrice) {
        this.exportBillPrice = exportBillPrice;
    }

    public Integer getExportBillStatus() {
        return exportBillStatus;
    }

    public void setExportBillStatus(Integer exportBillStatus) {
        this.exportBillStatus = exportBillStatus;
    }

    public Double getExportBillPaid() {
        return exportBillPaid;
    }

    public void setExportBillPaid(Double exportBillPaid) {
        this.exportBillPaid = exportBillPaid;
    }

    public Date getExportBillTime() {
        return exportBillTime;
    }

    public void setExportBillTime(Date exportBillTime) {
        this.exportBillTime = exportBillTime;
    }

    public Integer getExportConfirmStaffId() {
        return exportConfirmStaffId;
    }

    public void setExportConfirmStaffId(Integer exportConfirmStaffId) {
        this.exportConfirmStaffId = exportConfirmStaffId;
    }

    public Integer getExportSubmitStaffId() {
        return exportSubmitStaffId;
    }

    public void setExportSubmitStaffId(Integer exportSubmitStaffId) {
        this.exportSubmitStaffId = exportSubmitStaffId;
    }

    @Override
    public String toString() {
        return "ExportBill{" +
                "exportBillId='" + exportBillId + '\'' +
                ", exportBillCouponId=" + exportBillCouponId +
                ", exportBillSupplierId=" + exportBillSupplierId +
                ", exportBillGoodsBatchNumber='" + exportBillGoodsBatchNumber + '\'' +
                ", exportBillProductionDate=" + exportBillProductionDate +
                ", exportBillShelfLife=" + exportBillShelfLife +
                ", exportBillPrice=" + exportBillPrice +
                ", exportBillStatus=" + exportBillStatus +
                ", exportBillPaid=" + exportBillPaid +
                ", exportBillTime=" + exportBillTime +
                ", exportConfirmStaffId=" + exportConfirmStaffId +
                ", exportSubmitStaffId=" + exportSubmitStaffId +
                ", exportBillMark='" + exportBillMark + '\'' +
                '}';
    }
}
