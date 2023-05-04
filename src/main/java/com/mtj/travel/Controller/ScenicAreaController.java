package com.mtj.travel.Controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtj.travel.Service.ScenicAreaService;
import com.mtj.travel.entity.Result;
import com.mtj.travel.entity.ScenicArea;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/scenic", consumes = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class ScenicAreaController {

    @Autowired
    private ScenicAreaService scenicAreaService;

    // 新增景区信息
    @PostMapping
    public Result add(@RequestBody ScenicArea scenicArea) {
//        log.info(Arrays.toString(scenicArea.getPicture()));
        scenicArea.setId(null);
        boolean success = scenicAreaService.save(scenicArea);
        if (success) {
            return new Result("200", null, "添加成功");
        } else {
            return new Result("300", null, "添加失败");
        }
    }

    // 根据景区ID查询景区信息
    @GetMapping("/byId/{id}")
    public Result getById(@PathVariable("id") String id) {
        ScenicArea scenicArea = scenicAreaService.getById(id);
        if (scenicArea != null) {
            return new Result("200", scenicArea, "查询成功");
        } else {
            return new Result("404", null, "查询失败");
        }
    }

    // 根据景区名字查询景区信息
    @GetMapping("/byName/{name}")
    public Result getByName(@PathVariable("name") String name) {
        LambdaQueryWrapper<ScenicArea> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null, ScenicArea::getName, name);
        List<ScenicArea> scenicAreas = scenicAreaService.list(wrapper);

        if (scenicAreas != null) {
            return new Result("200", scenicAreas, "查询成功");
        } else {
            return new Result("404", null, "查询失败");
        }
    }

    // 分页查询景区信息
    @GetMapping("/page")
    public Result getPage(@RequestParam(value = "pageNum", defaultValue = "1") Long pageNum,
                            @RequestParam(value = "pageSize", defaultValue = "10") Long pageSize,
                            @RequestParam(value = "name", required = false) String name,
                            @RequestParam(value = "type", required = false) String type,
                            @RequestParam(value = "order", required = false) String order) {
        Page<ScenicArea> page = new Page<>(pageNum, pageSize);
        QueryWrapper<ScenicArea> wrapper = new QueryWrapper<>();
        if (name != null) {
            wrapper.like("name", name); // 如果传入了景区名，就添加模糊查询条件
        }
        if (type != null) {
            wrapper.eq("type", type); // 如果传入了类型，就添加等于类型的条件
        }
        if (order != null) {
            if (order.equalsIgnoreCase("asc")) {
                wrapper.orderByAsc("rating"); // 如果传入了排序方法，且为升序，就按照评分从低到高排序
            } else if (order.equalsIgnoreCase("desc")) {
                wrapper.orderByDesc("rating"); // 如果传入了排序方法，且为降序，就按照评分从高到低排序
            }
        } else {
            wrapper.orderByDesc("rating"); // 如果没有传入排序方法，就默认按照评分从高到低排序
        }
        return new Result("200", scenicAreaService.page(page, wrapper), "查询成功");
    }

    // 根据景区ID更新景区信息
    @PutMapping
    public Result updateById(@RequestBody ScenicArea scenicArea) {

        boolean success = scenicAreaService.updateById(scenicArea);
        if (success) {
            return new Result("200", null, "更新成功");
        } else {
            return new Result("400", null, "更新失败或ID不存在");
        }
    }

    // 根据景区ID删除景区信息
    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable("id") String id) {
        boolean success = scenicAreaService.removeById(id);
        if (success) {
            return new Result("200", null, "删除成功");
        } else {
            return new Result("400", null, "删除失败或ID不存在");
        }
    }
}