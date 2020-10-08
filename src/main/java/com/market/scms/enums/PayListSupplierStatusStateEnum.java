package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 11:47
 */
public enum PayListSupplierStatusStateEnum {
    CLOSED(1, "已结账"), UNSETTLED(0, "未结账");

    private int state;
    private String stateInfo;

    PayListSupplierStatusStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static PayListSupplierStatusStateEnum stateOf(int index) {
        for (PayListSupplierStatusStateEnum curEnum : values()) {
            if (curEnum.getState() == index) {
                return curEnum;
            }
        }
        return null;
    }
}
