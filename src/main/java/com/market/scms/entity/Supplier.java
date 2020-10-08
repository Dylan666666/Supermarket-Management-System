package com.market.scms.entity;

import java.util.Date;

/**
 * 供货商
 * @Author: Mr_OO
 * @Date: 2020/10/7 15:24
 */
public class Supplier {
    private Long supplierId;
    private String supplierName;
    private String supplierAddress;
    private Long supplierPhone;
    private String supplierPassword;
    private String supplierEmail;
    private String supplierFax;
    /**
     * 信誉属性
     */
    private Integer supplierStatus;
    /**
     * 货款结算
     */
    private Integer supplierPayment;

    private Date createTime;
    private Date lastEditTime;
    
}
