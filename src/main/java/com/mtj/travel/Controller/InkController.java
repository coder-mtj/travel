package com.mtj.travel.Controller;


import com.mtj.travel.Service.Impl.InkServiceImpl;
import com.mtj.travel.entity.Ink;
import com.mtj.travel.entity.Result;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping(value = "inks")
@Slf4j
public class InkController {
    @Autowired
    private InkServiceImpl inkService;

    /**
     * 上传Ink
     *
     * @param ink
     * @param file
     * @return
     */

    @PostMapping("/upload")
    public Result upload(@RequestBody Ink ink, MultipartFile file) {
        log.info("Ink/upload is running...");
        // 上传文件
        String fileName = UUID.randomUUID().toString().replace("-", "");
        String url = "c:/intel/ink/";
        // 将文件路径存入数据库
        ink.setUrl(url);
        try{
            // 判断文件夹是否存在
            File dir = new File(url);
            if(!dir.exists()){
                dir.mkdirs();
            }

            // 上传文件
            File uploadUrl = new File(url + fileName);
            file.transferTo(uploadUrl);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        // 保存数据
        inkService.save(ink);
        return new Result("200", null, "上传成功");
    }

    /**
     * 下载Ink
     *
     * @param ink
     * @param response
     */
    @GetMapping("/download/{fileName:.+}")
    public void download(@PathVariable("fileName") String fileName, HttpServletResponse response) throws IOException {
        log.info("download开始工作");
        // 从磁盘读取文件内容并构造输入流
        Path filePath = Paths.get("C:\\Intel\\" + fileName); // 文件路径可以通过查询数据库或解析 URL 参数获取
        InputStream inputStream = Files.newInputStream(filePath);

        // 获取 MIME 类型
        String mimeType = URLConnection.guessContentTypeFromStream(inputStream);

        // 设置 HTTP 响应头信息
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", "attachment; filename=" + encodeFilename(fileName));

        // 将文件内容写入 HTTP 响应输出流
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    private String encodeFilename(String fileName) {
        try {
            return URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return fileName.replaceAll("[^\\w\\d\\.\\-]", "_"); // 替换非法字符
        }
    }

}
