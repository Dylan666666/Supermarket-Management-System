package com.market.scms.bean;

import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/25 20:46
 */
public class PrimaryMenuTree {
    private Integer primaryMenuId;
    private String primaryMenuName;
    private Integer primaryMenuWeight;
    private List<SecondaryMenuTree> secondaryMenuTreeList;

    public Integer getPrimaryMenuId() {
        return primaryMenuId;
    }

    public void setPrimaryMenuId(Integer primaryMenuId) {
        this.primaryMenuId = primaryMenuId;
    }

    public String getPrimaryMenuName() {
        return primaryMenuName;
    }

    public void setPrimaryMenuName(String primaryMenuName) {
        this.primaryMenuName = primaryMenuName;
    }

    public Integer getPrimaryMenuWeight() {
        return primaryMenuWeight;
    }

    public void setPrimaryMenuWeight(Integer primaryMenuWeight) {
        this.primaryMenuWeight = primaryMenuWeight;
    }

    public List<SecondaryMenuTree> getSecondaryMenuTreeList() {
        return secondaryMenuTreeList;
    }

    public void setSecondaryMenuTreeList(List<SecondaryMenuTree> secondaryMenuTreeList) {
        this.secondaryMenuTreeList = secondaryMenuTreeList;
    }
}
