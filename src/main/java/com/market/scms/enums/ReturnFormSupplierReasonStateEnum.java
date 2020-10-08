package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 11:56
 */
public enum ReturnFormSupplierReasonStateEnum {
    QUALITY_PROBLEM(0, "质量问题"), DATE_PROBLEM(1, "日期问题");
    
    private int state;
    private String stateInfo;

    ReturnFormSupplierReasonStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static ReturnFormSupplierReasonStateEnum stateOf(int index) {
        for (ReturnFormSupplierReasonStateEnum curEnum : values()) {
            if (curEnum.getState() == index) {
                return curEnum;
            }
        }
        return null;
    }
}
