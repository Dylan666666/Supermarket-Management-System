package com.market.scms.util;

import com.market.scms.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 处理图片
 * @Author: Mr_OO
 * @Date: 2020/11/5 14:17
 */
public class ImageUtil {
    /**
     * 当前路径获取
     */
    private static String basePath01 = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static String basePath;
    /**
     * 日期格式
     */
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    /**
     * 随机数生成对象
     */
    private static final Random random = new Random();
    private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
    static {
        try {
            /**
             * 当前路径的编码规范，避免编码出错
             */
            basePath = URLDecoder.decode(basePath01,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 水印图片位置
     */
    private static File waterMark = new File(basePath + "/watermark.png");

    /**
     * 处理缩略图，并返回新生成图片的相对值路径
     * @param thumbnail
     * @param targetAddr
     * @return
     */
    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnail.getImageName());
        makeDirPath(targetAddr);
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativeAddr is: " + relativeAddr);
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current completeAddr is: " + PathUtil.getImgBasePath() + relativeAddr);
        try {
            //对接收的图片做大小限制处理，并添加水印，存储到指定位置
            Thumbnails.of(thumbnail.getImage()).size(200,200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(waterMark),0.25f)
                    .outputQuality(1f).toFile(dest);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return relativeAddr;
    }

    /**
     * 生成随机文件名，当前年月日小时分秒+五位随机数
     * @return
     */
    public static String getRandomFileName() {
        //获取随机的五位数
        int ranNum = random.nextInt(89999) + 10000;
        //当前年月日小时分秒
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr + ranNum;
    }

    /**
     * 获取输入流的扩展名
     * @param fileName
     * @return
     */
    private static String getFileExtension(String fileName ) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 创建目标路径所涉及到的目录，即 /home/project/image/xxx.jpg 
     * 自动创建所需的三个文件夹
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if(!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }
}
