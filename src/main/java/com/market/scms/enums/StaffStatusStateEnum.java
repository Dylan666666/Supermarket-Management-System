package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 10:43
 */
public enum StaffStatusStateEnum {
    JUST_REGISTERED(1001, "刚注册"), NORMAL(1000, "正常"),
    NO_PERMISSION(0, "无权限"), QUIT(-1, "离职");

    private int state;
    private String stateInfo;

    StaffStatusStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static StaffStatusStateEnum stateOf(int index) {
        for (StaffStatusStateEnum curEnum : values()) {
            if (curEnum.getState() == index) {
                return curEnum;
            }
        }
        return null;
    }
}
