package com.market.scms.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: Mr_OO
 * @Date: 2020/11/28 16:35
 */
public class RefundCustomerIdCreator {
    public static String get(int staffId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        String str = sdf.format(new Date());
        return str + staffId;
    }
}
