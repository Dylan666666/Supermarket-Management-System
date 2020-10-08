package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 10:50
 */
public enum SupplierPaymentStateEnum {
    ALL_SUPPORT(1, "预付款和一次性付款"), ADVANCE_CHARGE(1, "预付款"), 
    SINGLE_payment(-1, "一次性付款");

    private int state;
    private String stateInfo;

    SupplierPaymentStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static SupplierPaymentStateEnum stateOf(int index) {
        for (SupplierPaymentStateEnum curEnum : values()) {
            if (curEnum.getState() == index) {
                return curEnum;
            }
        }
        return null;
    }
}
