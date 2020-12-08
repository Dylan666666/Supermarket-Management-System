package com.market.scms.entity;

import java.io.Serializable;

/**
 * 产品类别
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/18 10:15
 */
public class GoodsCategory implements Serializable {
    private static final long serialVersionUID = -9005619073260854042L;
    private Integer categoryId;
    private String categoryName;
    private Integer stocktakingStaffId;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getStocktakingStaffId() {
        return stocktakingStaffId;
    }

    public void setStocktakingStaffId(Integer stocktakingStaffId) {
        this.stocktakingStaffId = stocktakingStaffId;
    }
}
