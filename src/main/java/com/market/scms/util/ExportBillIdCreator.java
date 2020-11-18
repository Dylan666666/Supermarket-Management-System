package com.market.scms.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 入库单ID生成器
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/18 20:35
 */
public class ExportBillIdCreator {
    public static String get(Long couponGoodsId) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
        String str = sdf.format(new Date());
        return str + couponGoodsId;
    }
}
