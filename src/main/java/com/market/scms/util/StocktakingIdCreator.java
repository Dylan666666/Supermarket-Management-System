package com.market.scms.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 盘点单号ID生成器
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/26 19:16
 */
public class StocktakingIdCreator {
    
    public static Long get(int num) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String str = sdf.format(new Date());
        str = str + (num + 1);
        Long cur = Long.valueOf(str);
        return cur;
    }

    public static String getDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str = sdf.format(new Date());
        return str;
    }
}
