package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/11 21:26
 */
public enum OrderFormStatusStateEnum {
    REFUSED(-1, "拒绝通过审核"), TO_BE_REVIEWED_BY_MANAGER(1, "待经理审核"),
    TO_BE_REVIEWED_BY_SUPPLIER(2, "待供应商审核"), TO_BE_GODOWN(3, "待入库");

    private int state;
    private String stateInfo;

    OrderFormStatusStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static OrderFormStatusStateEnum stateOf(int index) {
        for (OrderFormStatusStateEnum curEnum : values()) {
            if (curEnum.getState() == index) {
                return curEnum;
            }
        }
        return null;
    }
}
