package com.market.scms.entity.staff;

import java.io.Serializable;

/**
 * 职位表
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/9 21:15
 */
public class StaffPosition implements Serializable {
    private static final long serialVersionUID = -6880034094186707990L;
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
