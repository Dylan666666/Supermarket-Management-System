package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/27 19:31
 */
public enum DeliveryCheckOutStatusStateEnum {
    NO_CHECK(0, "未付款"), CHECKED(1, "已付款");

    private int state;
    private String stateInfo;

    DeliveryCheckOutStatusStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static DeliveryCheckOutStatusStateEnum stateOf(int index) {
        for (DeliveryCheckOutStatusStateEnum curEnum : values()) {
            if (curEnum.getState() == index) {
                return curEnum;
            }
        }
        return null;
    }
}
