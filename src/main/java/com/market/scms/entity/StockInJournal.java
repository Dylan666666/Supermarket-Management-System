package com.market.scms.entity;

import java.util.Date;

/**
 * 入库流水账
 * @Author: Mr_OO
 * @Date: 2020/10/7 15:56
 */
public class StockInJournal {
    private Long goodsId;
    private String stockInJournalSupplierName;
    private Integer stockInJournalId;
    private Date stockInJournalDate;
    private Double stockInJournalGoodsPrice;
    /**
     * 已付款项
     */
    private Integer stockInJournalPaid;
    private Integer stockInJournalNum;
}
