package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 12:11
 */
public enum PayListCustomerStatusStateEnum {
    UNSETTLED(1, "未结账"), CLOSED(0, "已结账");

    private int state;
    private String stateInfo;

    PayListCustomerStatusStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static PayListCustomerStatusStateEnum stateOf(int index) {
        for (PayListCustomerStatusStateEnum curEnum : values()) {
            if (curEnum.getState() == index) {
                return curEnum;
            }
        }
        return null;
    }
}
