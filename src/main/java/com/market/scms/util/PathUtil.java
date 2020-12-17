package com.market.scms.util;

import org.springframework.beans.factory.annotation.Value;

/**
 * 图片存储路径配置类
 * @Author: Mr_OO
 * @Date: 2020/11/5 14:23
 */
public class PathUtil {
    private static String winPath = "C:/scms/image";
    private static String linuxPath = "/scms/image";
    private static String goodsPath = "/goods/";

    /**
     * 获取基础路径
     * @return
     */
    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        String windows = "windows";
        if(os.toLowerCase().startsWith(windows)) {
            basePath = winPath;
        } else {
            basePath = linuxPath;
        }
        return basePath;
    }

    /**
     * 获取具体图片子路径
     * 
     * @param goodsCategoryId
     * @return
     */
    public static String getGoodsImagePath(int goodsCategoryId) {
        String imagePath = goodsPath + goodsCategoryId +  "/";
        return imagePath;
    }
}
