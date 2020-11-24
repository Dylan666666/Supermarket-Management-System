package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/24 17:04
 */
public enum DeliveryStatusStateEnum {
    START(0, "营业员初次发起"), SUCCESS(1, "库房管理员确认出库"),
    FAILURE(-1, "库房管理员驳回出库");

    private int state;
    private String stateInfo;

    DeliveryStatusStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static DeliveryStatusStateEnum stateOf(int index) {
        for (DeliveryStatusStateEnum curEnum : values()) {
            if (curEnum.getState() == index) {
                return curEnum;
            }
        }
        return null;
    }
}
