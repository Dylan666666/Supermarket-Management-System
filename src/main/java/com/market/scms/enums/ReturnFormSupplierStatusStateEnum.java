package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 12:00
 */
public enum ReturnFormSupplierStatusStateEnum {
    UNTREATED(0, "未处理"), PROCESSED(1, "已处理");

    private int state;
    private String stateInfo;

    ReturnFormSupplierStatusStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static ReturnFormSupplierStatusStateEnum stateOf(int index) {
        for (ReturnFormSupplierStatusStateEnum curEnum : values()) {
            if (curEnum.getState() == index) {
                return curEnum;
            }
        }
        return null;
    }
}
