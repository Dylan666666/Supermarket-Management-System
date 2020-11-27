package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/27 10:15
 */
public enum StocktakingAllStatusStateEnum {
    START(0, "初始状态待盘点"), 
    FINISH(2, "已更新至库存"), 
    QUIT(-1, "已取消盘点");

    private int state;
    private String stateInfo;

    StocktakingAllStatusStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static StocktakingAllStatusStateEnum stateOf(int index) {
        for (StocktakingAllStatusStateEnum curEnum : values()) {
            if (curEnum.getState() == index) {
                return curEnum;
            }
        }
        return null;
    }
}
