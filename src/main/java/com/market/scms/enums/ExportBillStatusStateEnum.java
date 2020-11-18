package com.market.scms.enums;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/18 19:10
 */
public enum ExportBillStatusStateEnum {
    START(0, "刚生成入库单"), WAREHOUSE_FIRST(1, "库管已完善信息"),
    WORKERS_SUCCESS(2, "职工检查通过"), WORKERS_FAILURE(-2, "职工检查未通过"),
    TO_STOCK(3, "入库成功"), WAREHOUSE_FAILURE(-1, "库管审核不通过");

    private int state;
    private String stateInfo;

    ExportBillStatusStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public static ExportBillStatusStateEnum stateOf(int index) {
        for (ExportBillStatusStateEnum curEnum : values()) {
            if (curEnum.getState() == index) {
                return curEnum;
            }
        }
        return null;
    }
}
