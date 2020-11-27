package com.market.scms.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 出库单零售单ID生成器
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/27 20:04
 */
public class RetailRecordIdCreator {
    public static String get(int staffId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        String str = sdf.format(new Date());
        return str + staffId;
    }
}
