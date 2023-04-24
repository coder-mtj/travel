package com.mtj.travel.Controller;


import com.mtj.travel.entity.Result;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "file")
@Slf4j
public class FilesController {

    @PostMapping("/upload")
    public Result handleFileUpload(@RequestParam("files") MultipartFile[] files) {
        log.info("upload请求正在服务！");
        List<String> filenames = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                return new Result("400", null, "上传失败，请选择文件");
            }

            try {
                // 生成文件名
                String filename = String.valueOf(UUID.randomUUID());
                int indexOf = file.getOriginalFilename().lastIndexOf(".");
                String extension = file.getOriginalFilename().substring(indexOf + 1);
                String fullName = filename + "." + extension;
                log.info("这是文件名:" + fullName);

                // 保存文件到本地
                byte[] bytes = file.getBytes();
                Path path = Paths.get("C:\\Intel\\" + fullName);
                Files.write(path, bytes);

                // 将文件名添加到列表中
                filenames.add(fullName);
            } catch (IOException e) {
                e.printStackTrace();
                return new Result("500", null, "上传失败");
            }
        }

        return new Result("200", filenames, "上传成功");
    }

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