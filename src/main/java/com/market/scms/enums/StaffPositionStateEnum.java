package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/10/8 10:28
 */
public enum StaffPositionStateEnum {
    GENERAL_MANAGER(1, "总经理"), ASSISTANT_MANAGER(2, "副经理"), 
    FINANCE(3, "财务"), WAREHOUSE_MANAGER(4, "库房管理员"),
    WORKERS(5, "职工"), SALES_CLERK(6, "营业员");
    
    private int state;
    private String stateInfo;

    StaffPositionStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
    
    public static StaffPositionStateEnum stateOf(int index) {
        for (StaffPositionStateEnum curEnum : values()) {
            if (curEnum.getState() == index) {
                return curEnum;
            }
        }
        return null;
    }
}
