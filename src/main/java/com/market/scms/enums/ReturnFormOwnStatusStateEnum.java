package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 12:08
 */
public enum ReturnFormOwnStatusStateEnum {
    STAFF_UNTREATED(1, "未处理"), STAFF_PROCESSED(0, "已处理");

    private int state;
    private String stateInfo;

    ReturnFormOwnStatusStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static ReturnFormOwnStatusStateEnum stateOf(int index) {
        for (ReturnFormOwnStatusStateEnum curEnum : values()) {
            if (curEnum.getState() == index) {
                return curEnum;
            }
        }
        return null;
    }
}
