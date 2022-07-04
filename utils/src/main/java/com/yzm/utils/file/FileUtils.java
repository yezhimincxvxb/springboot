package com.yzm.utils.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public enum FileUtils {
    INSTANCE;

    public static final String localPath = "D://file//";

    public boolean upload(MultipartFile file) {
        try {
            String fileName = file.getOriginalFilename();
            if (StringUtils.hasLength(fileName)) {
                log.info("filename={}", fileName);
                String format = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
                File dest = new File(localPath + "/" + format + "_" + fileName);
                if (!dest.exists()) dest.mkdirs();

                file.transferTo(dest);
                //dest.delete();
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
