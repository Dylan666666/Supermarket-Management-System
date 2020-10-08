package com.market.scms.entity;

/**
 * 供应商待付款项表
 * @Author: Mr_OO
 * @Date: 2020/10/7 16:00
 */
public class PayListSupplier {
    private Long stockInJournalId;
    private Double payListSupplierUnpaid;
    /**
     * 已付款项
     */
    private Integer payListSupplierStatus;
}
