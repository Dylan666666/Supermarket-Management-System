package com.market.scms.entity;

import java.util.Date;

/**
 * 出库单
 * @Author: Mr_OO
 * @Date: 2020/10/7 16:02
 */
public class OutboundOrder {
    private Long goodsId;
    private String outboundOrderSupplierName;
    private Long deliveryNoteId;
    private Date deliveryDate;
    private Double outboundOrderGoodsPrice;
    private Integer outboundOrderPaid;
    private Integer outboundOrderNum;
    /**
     * 出库状态
     */
    private Integer outboundOrderStatus;
    
}
