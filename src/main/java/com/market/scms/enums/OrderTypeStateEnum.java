package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 12:02
 */
public enum OrderTypeStateEnum {
    RETAIL_RETURN(2, "零售退货"), WHOLESALE_RETURN(1, "批发退货");

    private int state;
    private String stateInfo;

    OrderTypeStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static OrderTypeStateEnum stateOf(int index) {
        for (OrderTypeStateEnum curEnum : values()) {
            if (curEnum.getState() == index) {
                return curEnum;
            }
        }
        return null;
    }
}
