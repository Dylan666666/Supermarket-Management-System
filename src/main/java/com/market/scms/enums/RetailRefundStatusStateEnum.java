package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/27 20:14
 */
public enum RetailRefundStatusStateEnum {
    NO_REFUND(0, "未退款"), REFUNDED(1, "已退款");

    private int state;
    private String stateInfo;

    RetailRefundStatusStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static RetailRefundStatusStateEnum stateOf(int index) {
        for (RetailRefundStatusStateEnum curEnum : values()) {
            if (curEnum.getState() == index) {
                return curEnum;
            }
        }
        return null;
    }
}
