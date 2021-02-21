package com.youngchayoungcha.tastynote.util;

import com.youngchayoungcha.tastynote.config.FileConfig;

import java.io.File;

public class FileUtils {

    public static String getFileExtensions(String fileName) {
        if (fileName.lastIndexOf(".") != -1) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return "";
    }

    public static void deleteUploadedFileByUrl(String fileURL) {
        String filePath = getOriginalFileName(fileURL);
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    private static String getOriginalFileName(String fileURL){
        return fileURL.replace(FileConfig.uploadFileBaseUrl, FileConfig.uploadFileBasePath);
    }
}
