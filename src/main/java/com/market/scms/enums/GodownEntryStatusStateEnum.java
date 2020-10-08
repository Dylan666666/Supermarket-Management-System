package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 10:55
 */
public enum GodownEntryStatusStateEnum {
    MANAGER_REVIEWED(1, "经理已审核"), FINANCE_REVIEWED(2, "财务已审核"),
    CLERK_REVIEWED(3, "职工，库管已审核"), UNQUALIFIED(4, "数量或质量不合格"),
    ENTERED(5, "已登记入账"), INVALID(0, "已失效");

    private int state;
    private String stateInfo;

    GodownEntryStatusStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static GodownEntryStatusStateEnum stateOf(int index) {
        for (GodownEntryStatusStateEnum curEnum : values()) {
            if (curEnum.getState() == index) {
                return curEnum;
            }
        }
        return null;
    }
}
