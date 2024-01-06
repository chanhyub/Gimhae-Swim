package com.alijas.gimhaeswim.util;

import org.springframework.web.multipart.MultipartFile;

public class ParseMultipart {
    public static String getFileExtension(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename != null && originalFilename.lastIndexOf('.') != -1) {
            return originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
        } else {
            return null;
        }
    }
}
