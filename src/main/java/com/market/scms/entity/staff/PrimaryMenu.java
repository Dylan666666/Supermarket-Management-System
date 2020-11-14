package com.market.scms.entity.staff;

/**
 * 一级菜单表
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/9 21:13
 */
public class PrimaryMenu {
    private Integer primaryMenuId;
    private String primaryMenuName;
    private Integer primaryMenuWeight;

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
}
