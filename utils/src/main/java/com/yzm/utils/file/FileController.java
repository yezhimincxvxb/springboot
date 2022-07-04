package com.yzm.utils.file;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/file")
public class FileController {

    @GetMapping("/index")
    public String index() {
        return "file";
    }

    /**
     * 批量文件操作
     */
    @PostMapping("/batchUpload")
    public void batchUpload(HttpServletRequest request) {
        if (request instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = multiRequest.getFileMap();
            if (fileMap.values().size() > 0) {
                for (MultipartFile file : fileMap.values()) {
                    FileUtils.INSTANCE.upload(file);
                }
            }
        }
    }

    /**
     * 批量文件操作(name=file有多个)
     */
    @PostMapping("/uploads")
    public void uploads(@RequestParam("file") MultipartFile[] files) {
        if (files.length > 0) {
            for (MultipartFile file : files) {
                FileUtils.INSTANCE.upload(file);
            }
        }
    }

    /**
     * 单个文件操作(name=file)
     */
    @PostMapping("/upload")
    public void upload(@RequestParam("file") MultipartFile file) {
        FileUtils.INSTANCE.upload(file);
    }

    /**
     * 文件下载
     */
    @PostMapping("/download")
    public String download(HttpServletResponse response) {
        //待下载文件名
        String fileName = "202012211406_car1.png";
        response.setHeader("content-type", "image/png");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

        BufferedInputStream bis;
        OutputStream os;
        try {
            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File("D:/file/" + fileName)));

            byte[] buff = new byte[1024 * 8];
            int len;
            while ((len = bis.read(buff)) != -1) {
                os.write(buff, 0, len);
                os.flush();
            }

            bis.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

}
