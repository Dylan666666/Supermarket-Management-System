package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 18:46
 */
public enum CouponStatusStateEnum {
    ORDERING(0, "订货中"), ORDER_SUCCESS(1, "订货成功"),
    ORDER_FAILURE(-1, "订货失败");

    private int state;
    private String stateInfo;

    CouponStatusStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static CouponStatusStateEnum stateOf(int index) {
        for (CouponStatusStateEnum curEnum : values()) {
            if (curEnum.getState() == index) {
                return curEnum;
            }
        }
        return null;
    }
}
