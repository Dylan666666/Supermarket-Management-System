package com.market.scms.entity;

import java.util.Date;

/**
 * 出库流水账
 * @Author: Mr_OO
 * @Date: 2020/10/7 17:10
 */
public class OutboundJournal {
    private Long goodsId;
    private String outboundJournalSupplierName;
    private Long outboundJournalDeliveryNoteId;
    private Date outboundJournalDeliveryDate;
    private Double outboundJournalGoodsPrice;
    private Integer outboundJournalPaid;
    private Integer outboundJournalNum;
    
}
