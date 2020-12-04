package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/12/4 9:06
 */
public enum OrderTypeStateEnum {
    MORE(1, "批发"), One(2, "零售");

    private int state;
    private String stateInfo;

    OrderTypeStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static OrderTypeStateEnum stateOf(int index) {
        for (OrderTypeStateEnum curEnum : values()) {
            if (curEnum.getState() == index) {
                return curEnum;
            }
        }
        return null;
    }
}
