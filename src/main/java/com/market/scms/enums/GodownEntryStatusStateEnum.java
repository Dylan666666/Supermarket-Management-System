package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 10:55
 */
public enum GodownEntryStatusStateEnum {
    TO_BE_REVIEWED_BY_MANAGER(0, "待经理审核"), 
    APPROVED_BY_MANAGER(1, "经理审核通过（待财务打款）"),
    APPROVED_BY_BY_FINANCE(2, "财务审核通过并打款"),
    TO_BE_DELIVERED_BY_SUPPLIER(3, "供应商待发货"),
    DELIVERED_BY_SUPPLIER(4, "供应商已发货待验货"),
    APPROVED_BY_WORKERS(5, "职工审核成功"),
    ENTERED(6, "库管审核成功，已登记入账"),
    UNQUALIFIED(-2, "数量或质量不合格"), 
    FAILED_BY_MANAGER(-1, "经理审核不通过");

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
