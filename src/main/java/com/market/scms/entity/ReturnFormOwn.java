package com.market.scms.entity;

import java.util.Date;

/**
 * 零售批发退货表
 * @Author: Mr_OO
 * @Date: 2020/10/7 18:39
 */
public class ReturnFormOwn {
    private Long returnFormOwnId;
    private Long returnFormOwnGoodsId;
    private Date returnFormOwnTime;
    private String returnFormOwnReason;
    private Integer returnFormOwnNum;
    /**
     * 订单类型
     */
    private Integer orderType;
    /**
     * 退货状态
     */
    private Integer returnFormOwnStatus;

}
