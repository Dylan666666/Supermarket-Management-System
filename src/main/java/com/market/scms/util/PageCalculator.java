package com.market.scms.util;

/**
 * @Author: Mr_OO
 * @Date: 2020/3/2 18:54
 */
public class PageCalculator {
    public static int calculatorRowIndex(int pageIndex,int pageSize) {
        return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
    } 
}
