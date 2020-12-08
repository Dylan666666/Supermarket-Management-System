package com.market.scms.entity;

import java.io.Serializable;

/**
 * 销售单位类
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/18 10:15
 */
public class Unit implements Serializable {
    private static final long serialVersionUID = 6125632518553529676L;
    private Integer unitId;
    private String unitName;

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}
