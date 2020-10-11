package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/11 11:06
 */
public enum TokenTimeEnum {
    EXPIRE_TIME(24, "token存活时间");

    private int state;
    private String stateInfo;

    TokenTimeEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static TokenTimeEnum stateOf(int index) {
        for (TokenTimeEnum curEnum : values()) {
            if (curEnum.getState() == index) {
                return curEnum;
            }
        }
        return null;
    }
}
