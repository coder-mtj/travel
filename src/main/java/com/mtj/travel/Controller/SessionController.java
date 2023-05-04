package com.mtj.travel.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtj.travel.Service.SessionService;
import com.mtj.travel.entity.Result;
import com.mtj.travel.entity.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/session")
@Slf4j
@Transactional
public class SessionController {

    @Autowired
    private SessionService sessionService;

    /**
     * 添加场次记录
     */
    @PostMapping
    public Result addSession(@RequestBody Session session) {
        boolean result = sessionService.save(session);
        if (result) {
            return new Result("200", "success", null);
        } else {
            return new Result("500", null, "添加场次记录失败");
        }
    }

    /**
     * 获取指定场次记录
     */
    @GetMapping("/{id}")
    public Result getSession(@PathVariable String id) {
        Session session = sessionService.getById(id);
        if (session != null) {
            return new Result("200", session, null);  // 返回成功和查询结果
        } else {
            return new Result("404", null, "场次记录不存在");  // 返回客户端404错误
        }
    }

    /**
     * 更新场次记录
     */
    @PutMapping
    public Result updateSession(@RequestBody Session session) {
        boolean result = sessionService.updateById(session);
        if (result) {
            return new Result("200", "success", null);
        } else {
            return new Result("500", null, "更新场次记录失败");
        }
    }

    /**
     * 删除场次记录
     */
    @DeleteMapping("/{id}")
    public Result deleteSession(@PathVariable String id) {
        boolean result = sessionService.removeById(id);
        if (result) {
            return new Result("200", "success", null);
        } else {
            return new Result("500", null, "删除场次记录失败");
        }
    }

    @GetMapping("/search")
    public Result getSessionList(
            @RequestParam(required = false) String dateTime,
            @RequestParam(required = false, defaultValue = "asc") String sort,
            @RequestParam(required = false) String sessionLocation,
            @RequestParam(required = false) String sessionType,
            @RequestParam(required = false) Integer sessionDuration,
            @RequestParam(required = false) Double sessionPrice,
            @RequestParam(required = false) String scenicAreaName,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        // 构造查询条件
        LambdaQueryWrapper<Session> queryWrapper = new LambdaQueryWrapper<>();

        if (dateTime != null && !dateTime.isEmpty()) {
            queryWrapper.gt(Session::getSessionDateTime, dateTime);
        } else {
            queryWrapper.gt(Session::getSessionDateTime, LocalDateTime.now());
        }
        if (sessionLocation != null && !sessionLocation.isEmpty()) {
            queryWrapper.like(Session::getSessionLocation, sessionLocation);
        }
        if (sessionType != null && !sessionType.isEmpty()) {
            queryWrapper.eq(Session::getSessionType, sessionType);
        }
        if (sessionDuration != null) {
            queryWrapper.eq(Session::getSessionDuration, sessionDuration);
        }
        if (sessionPrice != null) {
            queryWrapper.eq(Session::getSessionPrice, sessionPrice);
        }
        if (scenicAreaName != null && !scenicAreaName.isEmpty()) {
            queryWrapper.like(Session::getScenicAreaName, scenicAreaName);
        }

        // 排序
        boolean isAsc = sort.equals("asc");
        if (isAsc) queryWrapper.orderByAsc(Session::getSessionDateTime);
        else queryWrapper.orderByDesc(Session::getSessionDateTime);

        // 分页
        Page<Session> sessionPage = new Page<>(page, size);

        // 查询并返回结果
        // 查询并返回结果
        return new Result("200", sessionService.page(sessionPage, queryWrapper), "查询成功");
    }

    /**
     * 通过id进行预约
     *
     * @return
     */
    @GetMapping(value = "reserve/{id}")
    public synchronized Result reserve(@PathVariable String id) {
        log.info("Reserve is running...");

        Session sessionServiceById = sessionService.getById(id);
        log.info(sessionServiceById.getReservedCount().toString());
        if (sessionServiceById.getMaxCount() == sessionServiceById.getReservedCount()) {
            return new Result("400", null, "预约已满");
        }
        sessionServiceById.setReservedCount(sessionServiceById.getReservedCount() + 1);
        log.info(sessionServiceById.getReservedCount().toString());
        sessionService.updateById(sessionServiceById);
        return new Result("200", sessionServiceById.getReservedCount(), "预约成功");
    }


    /**
     * 通过id获取场次数
     * @param id
     * @return
     */
    @GetMapping(value = "getReserved/{id}")
    public Result getReserved(@PathVariable String id) {
        LambdaQueryWrapper<Session> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(id != null, Session::getId, id);
        Session sessionServiceOne = sessionService.getOne(wrapper);

        if(sessionServiceOne != null){
            return new Result("200", sessionServiceOne.getReservedCount(), "查询成功");
        }
        else return new Result("404", null, "ID不存在");
    }
}
