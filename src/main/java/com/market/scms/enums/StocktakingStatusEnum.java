package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/26 19:44
 */
public enum StocktakingStatusEnum {
    START(0, "初始状态待盘点"), SECOND(1, "已盘点待提交"),
    FINISH(2, "已更新至库存"), QUIT(-1, "已取消盘点");

    private int state;
    private String stateInfo;

    StocktakingStatusEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static StocktakingStatusEnum stateOf(int index) {
        for (StocktakingStatusEnum curEnum : values()) {
            if (curEnum.getState() == index) {
                return curEnum;
            }
        }
        return null;
    }
}
