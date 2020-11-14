package com.market.scms.entity.staff;

/**
 * 职位表
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/9 21:15
 */
public class StaffPosition {
    private Integer staffPositionId;
    private String staffPositionName;

    public Integer getStaffPositionId() {
        return staffPositionId;
    }

    public void setStaffPositionId(Integer staffPositionId) {
        this.staffPositionId = staffPositionId;
    }

    public String getStaffPositionName() {
        return staffPositionName;
    }

    public void setStaffPositionName(String staffPositionName) {
        this.staffPositionName = staffPositionName;
    }
}
