package com.mtj.travel.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mtj.travel.Service.ScenicAreaService;
import com.mtj.travel.entity.Result;
import com.mtj.travel.entity.ScenicArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("scenic")
public class ScenicAreaController {
    @Autowired
    ScenicAreaService scenicAreaService;

    // 获取所有景区信息
    @GetMapping
    public Result getAllScenicAreas() {
        return new Result("0", scenicAreaService.list(), "Success");
    }

    // 分页获取景区信息
    @GetMapping("/page")
    public Result getScenicAreasByPage(@RequestParam(defaultValue = "1") int pageNumber, @RequestParam(defaultValue = "10") int pageSize) {
        Page<ScenicArea> page = new Page<>(pageNumber, pageSize);
        QueryWrapper<ScenicArea> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("created_at");
        return new Result("0", scenicAreaService.page(page, wrapper), "Success");
    }

    // 根据ID获取景区信息
    @GetMapping("/{id}")
    public Result getScenicAreaById(@PathVariable String id) {
        ScenicArea scenicArea = scenicAreaService.getById(id);
        if (scenicArea == null) {
            return new Result("404", null, "Scenic area not found");
        }
        return new Result("0", scenicArea, "Success");
    }

    // 新增景区信息
    @PostMapping
    public Result addScenicArea(@RequestParam("files") List<MultipartFile> files, @RequestParam("scenicArea") String scenicAreaStr) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ScenicArea scenicArea = mapper.readValue(scenicAreaStr, ScenicArea.class);

        // 创建文件夹
        String dirPath = "C:/intel"; // 将上传目录设置为 C 盘下的 intel 文件夹，您可以自行修改这个目录路径。
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 上传图片并获取 URL 列表
        List<String> urls = files.stream().map(file -> {
            try {
                // 拼接新的文件名
                String fileName = UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
                File uploadFile = new File(dir.getAbsolutePath() + "\\" + fileName);
                // 上传文件，并返回对应 URL
                file.transferTo(uploadFile);
                return uploadFile.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());

        // 将 URL 列表转成逗号分隔的字符串
        String urlStr = String.join(",", urls);

        // 将 url 字段设置为新的文件名列表
        scenicArea.setImageSet(urlStr);

        // 保存景区信息
        scenicAreaService.save(scenicArea);
        return new Result("0", null, "Success");
    }


    // 修改景区信息
    @PutMapping
    public Result updateScenicArea(@RequestParam("files") List<MultipartFile> files, @RequestParam("scenicArea") String scenicAreaStr) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ScenicArea scenicArea = mapper.readValue(scenicAreaStr, ScenicArea.class);

        //检查或创建路径
        String dirPath = "C://intel";
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 上传图片并获取 URL 列表
        List<String> urls = files.stream().map(file -> {
            try {
                // 拼接新的文件名
                String fileName = UUID.randomUUID().toString() + "." + StringUtils.getFilenameExtension(file.getOriginalFilename());
                File uploadFile = new File(dir.getAbsolutePath() + "\\" + fileName);
                // 上传文件，并返回对应 URL
                file.transferTo(uploadFile);
                return uploadFile.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());

        // 将 URL 列表转成逗号分隔的字符串
        String urlStr = String.join(",", urls);

        // 将 url 字段设置为新的文件名列表
        scenicArea.setImageSet(urlStr);

        scenicAreaService.updateById(scenicArea);
        return new Result("0", null, "Success");
    }

    // 删除景区信息
    @DeleteMapping("/{id}")
    public Result deleteScenicArea(@PathVariable String id) {
        boolean success = scenicAreaService.removeById(id);
        if (!success) {
            return new Result("404", null, "Scenic area not found");
        }
        return new Result("0", null, "Success");
    }
}
