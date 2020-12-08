package com.market.scms.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/25 20:46
 */
public class SecondaryMenuTree implements Serializable {
    private static final long serialVersionUID = -7922296698698588448L;
    private Integer secondaryMenuId;
    private Integer secondaryMenuWeight;
    private Integer primaryMenuId;
    private String secondaryMenuName;
    private String secondaryMenuUrl;
    private List<FunctionTree> functionTreeList;

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

    public List<FunctionTree> getFunctionTreeList() {
        return functionTreeList;
    }

    public void setFunctionTreeList(List<FunctionTree> functionTreeList) {
        this.functionTreeList = functionTreeList;
    }
}
