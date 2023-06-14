package com.sjh.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author 宋佳豪
 * @version 1.0
 */
public class PathUtils {

    /**
     * 根据传入文件名生成不重复文件名
     * @param fileName 传入文件名
     * @return 新文件名
     */
    public static String generateFilePath(String fileName) {
        // 根据日期生成路径
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String datePath = dateFormat.format(new Date());
        // 生成uuid,去除掉'-'
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        // 后缀与传入文件名后缀一致,以获取文件类型
        int index = fileName.lastIndexOf(".");
        String fileType = fileName.substring(index);
        return new StringBuilder().append(datePath).append(uuid).append(fileType).toString();
    }

}
