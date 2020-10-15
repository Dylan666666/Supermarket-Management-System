package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/15 10:56
 */
public enum ResultEnum {
    PASSED(1, "已通过"), REJECTED(0, "已拒绝通过");

    private int state;
    private String stateInfo;

    ResultEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static ResultEnum stateOf(int index) {
        for (ResultEnum curEnum : values()) {
            if (curEnum.getState() == index) {
                return curEnum;
            }
        }
        return null;
    }
}
