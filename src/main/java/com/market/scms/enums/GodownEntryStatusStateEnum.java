package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 10:55
 */
public enum GodownEntryStatusStateEnum {
    TO_BE_REVIEWED_BY_MANAGER(1, "待经理审核"), TO_BE_REVIEWED_BY_FINANCE(2, "待财务审核并打款"),
    TO_BE_REVIEWED_BY_CLERK(3, "待职工，库管审核质量和数量"),ENTERED(4, "已登记入账"),
    UNQUALIFIED(1000, "数量或质量不合格"), INVALID(-1, "已失效");

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
