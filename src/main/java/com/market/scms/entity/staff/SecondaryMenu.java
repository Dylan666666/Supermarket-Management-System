package com.market.scms.entity.staff;

import java.io.Serializable;

/**
 * 二级菜单表
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/9 21:13
 */
public class SecondaryMenu implements Serializable {
    private static final long serialVersionUID = -2519156972746780773L;
    private Integer secondaryMenuId;
    private Integer secondaryMenuWeight;
    private Integer primaryMenuId;
    private String secondaryMenuName;
    private String secondaryMenuUrl;

    public Integer getSecondaryMenuId() {
        return secondaryMenuId;
    }

    public void setSecondaryMenuId(Integer secondaryMenuId) {
        this.secondaryMenuId = secondaryMenuId;
    }

    public Integer getSecondaryMenuWeight() {
        return secondaryMenuWeight;
    }

    public void setSecondaryMenuWeight(Integer secondaryMenuWeight) {
        this.secondaryMenuWeight = secondaryMenuWeight;
    }

    public Integer getPrimaryMenuId() {
        return primaryMenuId;
    }

    public void setPrimaryMenuId(Integer primaryMenuId) {
        this.primaryMenuId = primaryMenuId;
    }

    public String getSecondaryMenuName() {
        return secondaryMenuName;
    }

    public void setSecondaryMenuName(String secondaryMenuName) {
        this.secondaryMenuName = secondaryMenuName;
    }

    public String getSecondaryMenuUrl() {
        return secondaryMenuUrl;
    }

    public void setSecondaryMenuUrl(String secondaryMenuUrl) {
        this.secondaryMenuUrl = secondaryMenuUrl;
    }
}
