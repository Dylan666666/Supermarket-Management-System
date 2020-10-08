package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 10:47
 */
public enum SupplierStatusStateEnum {
    GOOD(1, "良好"), BLACKLIST(-1, "黑名单");

    private int state;
    private String stateInfo;

    SupplierStatusStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static SupplierStatusStateEnum stateOf(int index) {
        for (SupplierStatusStateEnum curEnum : values()) {
            if (curEnum.getState() == index) {
                return curEnum;
            }
        }
        return null;
    }
}
