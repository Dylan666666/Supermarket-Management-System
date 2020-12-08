package com.market.scms.entity.staff;

import java.io.Serializable;

/**
 * 职工功能表
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/9 21:14
 */
public class StaffJurisdiction implements Serializable {
    private static final long serialVersionUID = -5943331020494443996L;
    private Integer staffId;
    private Integer functionId;
    private Integer jurisdictionStatus;

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Integer getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    public Integer getJurisdictionStatus() {
        return jurisdictionStatus;
    }

    public void setJurisdictionStatus(Integer jurisdictionStatus) {
        this.jurisdictionStatus = jurisdictionStatus;
    }
}
