package com.mtj.travel.Controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtj.travel.Service.ScenicAreaService;
import com.mtj.travel.entity.Result;
import com.mtj.travel.entity.ScenicArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/scenic-areas")
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
    public Result getScenicAreasByPage(@RequestParam(defaultValue = "1") int pageNumber,
                                       @RequestParam(defaultValue = "10") int pageSize) {
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
    public Result addScenicArea(@RequestBody ScenicArea scenicArea) {
        scenicAreaService.save(scenicArea);
        return new Result("0", null, "Success");
    }

    // 修改景区信息
    @PutMapping("/{id}")
    public Result updateScenicArea(@PathVariable String id, @RequestBody ScenicArea scenicArea) {
        ScenicArea dbScenicArea = scenicAreaService.getById(id);
        if (dbScenicArea == null) {
            return new Result("404", null, "Scenic area not found");
        }

        scenicArea.setId(id); // 强制使用URL中传入的ID

        boolean success = scenicAreaService.updateById(scenicArea);
        if (!success) {
            return new Result("500", null, "Failed to update scenic area");
        }
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
