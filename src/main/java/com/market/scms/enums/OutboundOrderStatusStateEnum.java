package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 11:49
 */
public enum OutboundOrderStatusStateEnum {
    MANAGER_REVIEWED(1, "经理已审核"), SALESPERSON_CONFIRM(2, "营业员已确定"),
    ENTERED(3, "已计入出库");

    private int state;
    private String stateInfo;

    OutboundOrderStatusStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static OutboundOrderStatusStateEnum stateOf(int index) {
        for (OutboundOrderStatusStateEnum curEnum : values()) {
            if (curEnum.getState() == index) {
                return curEnum;
            }
        }
        return null;
    }
}
