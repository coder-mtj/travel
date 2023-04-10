package com.mtj.travel.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtj.travel.Service.SouvenirService;
import com.mtj.travel.entity.Result;
import com.mtj.travel.entity.Souvenir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/souvenirs")
public class SouvenirController {

    private final String UPLOAD_DIR = "C:/intel/souvenir";  // 图片上传路径

    @Autowired
    private SouvenirService souvenirService;

    /**
     * 保存纪念品
     */
    @PostMapping("/")
    public Result saveSouvenir(@RequestParam("file") MultipartFile file,
                               @RequestBody Souvenir souvenir) throws IOException {
        if (!file.isEmpty()) {
            // 保存图片文件
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            File uploadFile = new File(dir.getAbsolutePath() + "/" + filename);
            file.transferTo(uploadFile);
            souvenir.setUrl(uploadFile.getAbsolutePath());
        }

        souvenirService.save(souvenir);
        return new Result("200", souvenir, "保存纪念品成功");
    }

    /**
     * 更新纪念品
     */
    @PutMapping("/{id}")
    public Result updateSouvenir(@PathVariable Long id,
                                 @RequestParam(value = "file", required = false) MultipartFile file,
                                 @RequestBody Souvenir souvenir) throws IOException {
        Souvenir existingSouvenir = souvenirService.getById(id);
        if (existingSouvenir == null) {
            return new Result("404", null, "找不到该纪念品");
        }

        if (!file.isEmpty()) {
            // 保存图片文件
            File dir = new File(UPLOAD_DIR);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String filename = StringUtils.cleanPath(file.getOriginalFilename());
            File uploadFile = new File(dir.getAbsolutePath() + "/" + filename);
            file.transferTo(uploadFile);
            souvenir.setUrl(uploadFile.getAbsolutePath());

            // 删除原有图片文件
            File oldFile = new File(existingSouvenir.getUrl());
            if (oldFile.exists()) {
                oldFile.delete();
            }
        } else {
            // 保留原有图片文件路径
            souvenir.setUrl(existingSouvenir.getUrl());
        }

        souvenir.setId(id);
        souvenirService.updateById(souvenir);
        return new Result("200", souvenir, "更新纪念品成功");
    }

    /**
     * 删除纪念品
     */
    @DeleteMapping("/{id}")
    public Result deleteSouvenir(@PathVariable Long id) {
        Souvenir existingSouvenir = souvenirService.getById(id);
        if (existingSouvenir == null) {
            return new Result("404", null, "找不到该纪念品");
        }

        // 删除图片文件
        File file = new File(existingSouvenir.getUrl());
        if (file.exists()) {
            file.delete();
        }

        souvenirService.removeById(id);
        return new Result("204", null, "删除纪念品成功");
    }

    /**
     * 获取所有纪念品列表
     */
    @GetMapping("/")
    public Result getAllSouvenirs() {
        List<Souvenir> souvenirs = souvenirService.list();
        if (souvenirs.isEmpty()) {
            return new Result("204", null, "没有找到纪念品");
        }
        return new Result("200", souvenirs, "获取所有纪念品成功");
    }

    /**
     * 分页获取纪念品列表
     */
    @GetMapping("/page")
    public Result getSouvenirPage(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        Page<Souvenir> page = new Page<>(pageNum, pageSize);
        souvenirService.page(page);
        if (page.getRecords().isEmpty()) {
            return new Result("204", null, "没有找到纪念品");
        }
        return new Result("200", page, "分页获取纪念品列表成功");
    }

    /**
     * 通过ID查找纪念品
     */
    @GetMapping("/{id}")
    public Result getSouvenirById(@PathVariable Long id) {
        Souvenir souvenir = souvenirService.getById(id);
        if (souvenir == null) {
            return new Result("404", null, "找不到该纪念品");
        }
        return new Result("200", souvenir, "获取纪念品详情成功");
    }

    /**
     * 通过ID删除纪念品
     */
    @DeleteMapping("/{id}/delete")
    public Result deleteSouvenirById(@PathVariable Long id) {
        Souvenir existingSouvenir = souvenirService.getById(id);
        if (existingSouvenir == null) {
            return new Result("404", null, "找不到该纪念品");
        }

        // 删除图片文件
        File file = new File(existingSouvenir.getUrl());
        if (file.exists()) {
            file.delete();
        }

        souvenirService.removeById(id);
        return new Result("204", null, "删除纪念品成功");
    }

    /**
     * 查找纪念品（根据名称）
     */
    @GetMapping("/find")
    public Result findSouvenirsByName(@RequestParam(value = "name", required = false) String name) {
        QueryWrapper<Souvenir> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", "%" + name + "%");
        }
        List<Souvenir> souvenirs = souvenirService.list(queryWrapper);
        if (souvenirs.isEmpty()) {
            return new Result("204", null, "没有找到符合条件的纪念品");
        }
        return new Result("200", souvenirs, "查找纪念品成功");
    }
}
