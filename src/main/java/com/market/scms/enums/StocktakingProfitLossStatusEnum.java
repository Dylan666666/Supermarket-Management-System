package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/26 19:47
 */
public enum StocktakingProfitLossStatusEnum {
    SUCCESS(1, "盘盈"), LOSE(-1, "盘亏"),
    NORMAL(0, "无盈亏");

    private int state;
    private String stateInfo;

    StocktakingProfitLossStatusEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static StocktakingProfitLossStatusEnum stateOf(int index) {
        for (StocktakingProfitLossStatusEnum curEnum : values()) {
            if (curEnum.getState() == index) {
                return curEnum;
            }
        }
        return null;
    }
}
