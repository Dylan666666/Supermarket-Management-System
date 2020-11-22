package com.market.scms.util;

import java.text.DecimalFormat;

/**
 * 浮点数工具
 * 
 * @Author: Mr_OO
 * @Date: 2020/11/22 12:46
 */
public class DoubleUtil {
    public static Double get(Double num) {
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.valueOf(df.format(num));
    }
}
