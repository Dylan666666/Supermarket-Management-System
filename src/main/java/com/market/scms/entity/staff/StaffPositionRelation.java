package com.market.scms.entity.staff;

import java.io.Serializable;

/**
 * 职工职位表
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/9 21:16
 */
public class StaffPositionRelation implements Serializable {
    private static final long serialVersionUID = 2152292079056954771L;
    private Integer staffId;
    private Integer staffPositionId;
    private Integer staffPositionStatus;

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public Integer getStaffPositionId() {
        return staffPositionId;
    }

    public void setStaffPositionId(Integer staffPositionId) {
        this.staffPositionId = staffPositionId;
    }

    public Integer getStaffPositionStatus() {
        return staffPositionStatus;
    }

    public void setStaffPositionStatus(Integer staffPositionStatus) {
        this.staffPositionStatus = staffPositionStatus;
    }
}
