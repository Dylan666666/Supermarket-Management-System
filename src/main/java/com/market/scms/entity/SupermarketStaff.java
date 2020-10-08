package com.market.scms.entity;

import java.util.Date;

/**
 * 超市职员
 * @Author: Mr_OO
 * @Date: 2020/10/7 15:28
 */
public class SupermarketStaff {
    private Long staffId;
    private String staffName;
    private String staffPassword;
    private Long staffPhone;
    /**
     * 职位属性
     */
    private Integer staffPosition;
    /**
     * 职工状态属性
     */
    private Integer staffStatus;

    private Date createTime;
    private Date lastEditTime;
    
}
