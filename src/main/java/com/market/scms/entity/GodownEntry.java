package com.market.scms.entity;

import java.util.Date;

/**
 * 入库单
 * @Author: Mr_OO
 * @Date: 2020/10/7 15:45
 */
public class GodownEntry {
    private Long godownEntryGoodsId;
    private String godownEntrySupplierName;
    private Long godownEntryId;
    private Date godownEntryDate;
    private Double godownEntryGoodsPrice;
    private Integer godownEntryPaid;
    private Integer godownEntryNum;
    /**
     *入库状态
     */
    private Integer godownEntryStatus;
    
}
