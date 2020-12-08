package com.market.scms.bean;

import java.io.Serializable;

/**
 * staff中转对象 Staff+staff_position_relation+staff_position
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/24 9:33
 */
public class StaffA implements Serializable {
    private static final long serialVersionUID = 6382612292486543090L;
    private Integer staffId;
    private String staffName;
    private String staffPassword;
    private String staffPhone;
    private Integer staffStatus;
    private Integer staffPositionId;
    private String staffPositionName;
    private Integer staffPositionStatus;
    
    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffPassword() {
        return staffPassword;
    }

    public void setStaffPassword(String staffPassword) {
        this.staffPassword = staffPassword;
    }

    public String getStaffPhone() {
        return staffPhone;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
    }

    public Integer getStaffStatus() {
        return staffStatus;
    }

    public void setStaffStatus(Integer staffStatus) {
        this.staffStatus = staffStatus;
    }

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

    public Integer getStaffPositionStatus() {
        return staffPositionStatus;
    }

    public void setStaffPositionStatus(Integer staffPositionStatus) {
        this.staffPositionStatus = staffPositionStatus;
    }
}
