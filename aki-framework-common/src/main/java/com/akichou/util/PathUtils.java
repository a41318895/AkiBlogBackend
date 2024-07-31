package com.akichou.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Path Utility
 *
 * <p>
 * Modify filename, editing directory.
 * </p>
 *
 * @author Aki Chou
 * @date 2024/06/19 Wed.
 */
public class PathUtils {

    public static String generateFilePath(String originalFilename){
        // Generate path via date    20xx/xx/xx/
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd/");
        String datePath = simpleDateFormat.format(new Date());
        // uuid -> be filename
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        int index = originalFilename.lastIndexOf(".");
        // test.jpg -> .jpg
        String fileType = originalFilename.substring(index);

        //return new StringBuilder().append(datePath).append(uuid).append(fileType).toString();
        return datePath + uuid + fileType;
    }
}