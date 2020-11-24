package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/24 15:23
 */
public enum DeliveryRefundStatusStateEnum {
    NO_REFUND(0, "未发生退款"), REFUND(1, "已发生退款");

    private int state;
    private String stateInfo;

    DeliveryRefundStatusStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static DeliveryRefundStatusStateEnum stateOf(int index) {
        for (DeliveryRefundStatusStateEnum curEnum : values()) {
            if (curEnum.getState() == index) {
                return curEnum;
            }
        }
        return null;
    }
}
