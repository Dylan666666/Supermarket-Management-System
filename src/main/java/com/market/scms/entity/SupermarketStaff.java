package com.market.scms.entity;

import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 超市职员
 * @Author: Mr_OO
 * @Date: 2020/10/7 15:28
 */
public class SupermarketStaff implements Serializable {
    private static final long serialVersionUID = 3449541084534768055L;
    
    private Integer staffId;
    private String staffName;
    private String staffPassword;
    private String staffPhone;
    /**
     * 职位属性
     */
    private Integer staffPosition;
    /**
     * 职工状态属性
     */
    private Integer staffStatus;
    private Date createTime;
    private Date lastEditTime;

    /**
     * token 登陆凭证
     */
    private String token;
    /**
     * token 过期时间
     */
    private LocalDateTime expireTime;
    /**
     *  登录时间
     */
    private LocalDateTime loginTime;

    /**
     * 密码盐
     */
    private String salt;
    
    public String getStaffPhone() {
        return staffPhone;
    }

    public void setStaffPhone(String staffPhone) {
        this.staffPhone = staffPhone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
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

    public Integer getStaffPosition() {
        return staffPosition;
    }

    public void setStaffPosition(Integer staffPosition) {
        this.staffPosition = staffPosition;
    }

    public Integer getStaffStatus() {
        return staffStatus;
    }

    public void setStaffStatus(Integer staffStatus) {
        this.staffStatus = staffStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    @Override
    public String toString() {
        return "测试：SupermarketStaff{" +
                "staffId=" + staffId +
                ", staffName='" + staffName + '\'' +
                ", staffPassword='" + staffPassword + '\'' +
                ", staffPhone='" + staffPhone + '\'' +
                ", staffPosition=" + staffPosition +
                ", staffStatus=" + staffStatus +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                ", token='" + token + '\'' +
                ", expireTime=" + expireTime +
                ", loginTime=" + loginTime +
                ", salt='" + salt + '\'' +
                '}';
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
