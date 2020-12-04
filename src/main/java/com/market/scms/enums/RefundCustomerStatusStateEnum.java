package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/12/4 9:09
 */
public enum RefundCustomerStatusStateEnum {
    SALE_CLERK(0, "营业员已退货"), WAE(1, "库管已处理退货");

    private int state;
    private String stateInfo;

    RefundCustomerStatusStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static RefundCustomerStatusStateEnum stateOf(int index) {
        for (RefundCustomerStatusStateEnum curEnum : values()) {
            if (curEnum.getState() == index) {
                return curEnum;
            }
        }
        return null;
    }
}
