package com.mtj.travel.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtj.travel.Service.ScenicAreaService;
import com.mtj.travel.entity.ScenicArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scenic")
public class ScenicAreaController {

    @Autowired
    private ScenicAreaService scenicAreaService;

    // 新增景区信息
    @PostMapping
    public ResponseEntity add(@RequestBody ScenicArea scenicArea) {
        boolean success = scenicAreaService.save(scenicArea);
        if (success) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 根据景区ID查询景区信息
    @GetMapping("/{id}")
    public ResponseEntity<ScenicArea> getById(@PathVariable("id") String id) {
        ScenicArea scenicArea = scenicAreaService.getById(id);
        if (scenicArea != null) {
            return ResponseEntity.ok(scenicArea);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 分页查询景区信息
    @GetMapping("/page")
    public ResponseEntity<Page<ScenicArea>> getPage(@RequestParam(value = "pageNum", defaultValue = "1") Long pageNum,
                                                    @RequestParam(value = "pageSize", defaultValue = "10") Long pageSize) {
        Page<ScenicArea> page = new Page<>(pageNum, pageSize);
        QueryWrapper<ScenicArea> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("rating"); // 按照评分从高到低排序
        return ResponseEntity.ok(scenicAreaService.page(page, wrapper));
    }

    // 根据景区ID更新景区信息
    @PutMapping("/{id}")
    public ResponseEntity updateById(@PathVariable("id") String id, @RequestBody ScenicArea scenicArea) {
        scenicArea.setId(id);
        boolean success = scenicAreaService.updateById(scenicArea);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 根据景区ID删除景区信息
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") String id) {
        boolean success = scenicAreaService.removeById(id);
        if (success) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}