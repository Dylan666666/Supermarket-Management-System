package com.market.scms.bean;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/25 20:46
 */
public class FunctionTree {
    private Integer functionId;
    private String functionUrl;
    private Integer functionWeight;
    private String functionName;
    private Integer secondaryMenuId;
    private Integer isSelected;

    public Integer getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Integer functionId) {
        this.functionId = functionId;
    }

    public String getFunctionUrl() {
        return functionUrl;
    }

    public void setFunctionUrl(String functionUrl) {
        this.functionUrl = functionUrl;
    }

    public Integer getFunctionWeight() {
        return functionWeight;
    }

    public void setFunctionWeight(Integer functionWeight) {
        this.functionWeight = functionWeight;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public Integer getSecondaryMenuId() {
        return secondaryMenuId;
    }

    public void setSecondaryMenuId(Integer secondaryMenuId) {
        this.secondaryMenuId = secondaryMenuId;
    }

    public Integer getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Integer isSelected) {
        this.isSelected = isSelected;
    }
}
