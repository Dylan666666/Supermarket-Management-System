package com.market.scms.entity;

import java.util.Date;

/**
 * 入库流水账
 * @Author: Mr_OO
 * @Date: 2020/10/7 15:56
 */
public class StockJournal {
    private Long stockJournalId;
    private Long stockJournalGoodsId;
    private Long stockJournalGodownEntryId;
    private Long stockJournalSupplierId;
    private Date stockJournalDate;
    private Double stockJournalGoodsPrice;
    private Double stockJournalPaid;
    private Integer stockJournalNum;

    public Long getStockJournalId() {
        return stockJournalId;
    }

    public void setStockJournalId(Long stockJournalId) {
        this.stockJournalId = stockJournalId;
    }

    public Long getStockJournalGoodsId() {
        return stockJournalGoodsId;
    }

    public void setStockJournalGoodsId(Long stockJournalGoodsId) {
        this.stockJournalGoodsId = stockJournalGoodsId;
    }

    public Long getStockJournalGodownEntryId() {
        return stockJournalGodownEntryId;
    }

    public void setStockJournalGodownEntryId(Long stockJournalGodownEntryId) {
        this.stockJournalGodownEntryId = stockJournalGodownEntryId;
    }

    public Long getStockJournalSupplierId() {
        return stockJournalSupplierId;
    }

    public void setStockJournalSupplierId(Long stockJournalSupplierId) {
        this.stockJournalSupplierId = stockJournalSupplierId;
    }

    public Date getStockJournalDate() {
        return stockJournalDate;
    }

    public void setStockJournalDate(Date stockJournalDate) {
        this.stockJournalDate = stockJournalDate;
    }

    public Double getStockJournalGoodsPrice() {
        return stockJournalGoodsPrice;
    }

    public void setStockJournalGoodsPrice(Double stockJournalGoodsPrice) {
        this.stockJournalGoodsPrice = stockJournalGoodsPrice;
    }

    public Double getStockJournalPaid() {
        return stockJournalPaid;
    }

    public void setStockJournalPaid(Double stockJournalPaid) {
        this.stockJournalPaid = stockJournalPaid;
    }

    public Integer getStockJournalNum() {
        return stockJournalNum;
    }

    public void setStockJournalNum(Integer stockJournalNum) {
        this.stockJournalNum = stockJournalNum;
    }
}
