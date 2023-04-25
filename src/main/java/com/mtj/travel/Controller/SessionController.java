package com.mtj.travel.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtj.travel.Service.SessionService;
import com.mtj.travel.entity.Result;
import com.mtj.travel.entity.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping(value = "session")
@Slf4j
public class SessionController {
    @Autowired
    public SessionService sessionService;

    /**
     * 通过id获取场次
     *
     * @param id
     * @return
     */
    @GetMapping(value = "{id}")
    public ResponseEntity<Session> getById(@PathVariable String id) {
        // 使用debug级别的日志
        log.debug("getById is Loading...");

        Session session = sessionService.getById(id);

        // 检查session是否为null
        if (session == null) {
            // 返回404状态码
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(session);
    }

    /**
     * 新增场次
     *
     * @param
     * @return
     */
    @PostMapping
    public Result addSession(@RequestBody Session session) {
        //debug级别打印
        log.debug("addSession is Loading...");
        //如果为空
        if (session == null) {
            return new Result("404", null, "不可传入空对象");
        }

        //如果不为空
        //获取场次的日期和时间
        Date sessionDate = session.getSessionDate();
        Date sessionTime = session.getSessionTime();

        //分别查询是否有日期和时间的实体
        LambdaQueryWrapper<Session> wrapper = new LambdaQueryWrapper<>();

        wrapper.like(session.getSessionDate() != null, Session::getSessionDate, session.getSessionDate()).like(session.getSessionTime() != null, Session::getSessionTime, session.getSessionTime());

        Session serviceOne = sessionService.getOne(wrapper);

        //检查数据库中是否有重复的日期和时间
        if (serviceOne != null) {
            //返回一个错误信息
            return new Result("400", null, "不可保存重复的场次");
        }
        //否则保存场次
        sessionService.save(session);
        return new Result("200", null, "保存成功");
    }

    /**
     * 通过ID删除实体
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "{id}")
    public Result deleteById(@PathVariable String id) {
        log.debug("deleteById is Loading...");

        LambdaQueryWrapper<Session> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Session::getId, id);
        Session sessionServiceOne = sessionService.getOne(wrapper);

        if (sessionServiceOne == null) {
            return new Result("404", null, "ID所属实体不存在");
        } else {
            sessionService.removeById(id);
            return new Result("200", null, "删除成功");
        }
    }

    /**
     * 通过ID更改场次
     *
     * @param session
     * @return
     */
    @PutMapping
    public Result modify(@RequestBody Session session) {
        log.debug("modify is Loading...");

        //判断是否存在该实体
        LambdaQueryWrapper<Session> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(session.getId() != null, Session::getId, session.getId());
        Session sessionServiceOne = sessionService.getOne(wrapper);
        //若实体不存在则返回错误
        if (sessionServiceOne == null) {
            return new Result("404", null, "ID所处实体不存在");
        }

        sessionService.updateById(session);

        return new Result("200", null, "删除成功");

    }

    /**
     * 普通分别查询
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping(value = "{pageSize}/{page}")
    public Result getPages(@PathVariable Long page, @PathVariable Long pageSize) {
        log.debug("getPages is Loading...");

        //默认按照日期升序进行排序
        Page<Session> sessionPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Session> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Session::getSessionDate, Session::getSessionTime)
                //大于现在时间
                .gt(Session::getSessionDate, LocalDateTime.now().toLocalDate());

        Page<Session> resultPage = sessionService.page(sessionPage, wrapper);

        return new Result("200", resultPage, "查询成功");
    }

    /**
     * 按照时间的升序进行排序
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("sorted")
    public Result getSessionsSorted(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<Session> sessionPage = new Page<>(page, size);
        LambdaQueryWrapper<Session> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(Session::getSessionDate)
                .orderByAsc(Session::getSessionTime)
                //大于现在时间
                .gt(Session::getSessionDate, LocalDateTime.now().toLocalDate());

        Page<Session> resultPage = sessionService.page(sessionPage, wrapper);

        return new Result("200", resultPage.getRecords(), "查询成功");
    }

    /**
     * 根据日期降序排序
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "sortedDesc")
    public Result getSessionSortedDesc(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        Page<Session> sessionPage = new Page<>(page, size);
        LambdaQueryWrapper<Session> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Session::getSessionDate)
                .orderByDesc(Session::getSessionTime)
                //大于现在时间
                .gt(Session::getSessionDate, LocalDateTime.now().toLocalDate());

        Page<Session> resultPage = sessionService.page(sessionPage, wrapper);

        return new Result("200", resultPage.getRecords(), "查询成功");
    }


    /**
     * 通过日期、时间、景区名字、场次类型进行日期、时间的分页查询
     *
     * @param page
     * @param size
     * @param date
     * @param time
     * @param scenicAreaName
     * @param sessionType
     * @return
     */
    @GetMapping("search")
    public Result searchSessions(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
            @RequestParam("time") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) Date time,
            @RequestParam("scenicAreaName") String scenicAreaName,
            @RequestParam("sessionType") String sessionType) {

        Page sessionPage = new Page(page, size);

        LambdaQueryWrapper<Session> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(date != null, Session::getSessionDate, date)
                //大于现在时间
                .gt(Session::getSessionDate, LocalDateTime.now().toLocalDate())
                .like(time != null, Session::getSessionTime, time)
                .like(scenicAreaName != null, Session::getScenicAreaName, scenicAreaName)
                .like(sessionType != null, Session::getSessionType, sessionType)
                .orderByAsc(Session::getSessionDate)
                .orderByAsc(Session::getSessionTime);

        Page resultPage = sessionService.page(sessionPage, wrapper);

        return new Result("200", resultPage.getRecords(), "查询成功");
    }

    /**
     * 通过景区名字进行查询按照日期时间降序进行排序
     *
     * @param page
     * @param size
     * @param scenicAreaName
     * @return
     */
    @GetMapping("searchByScenicAreaName")
    public Result searchByScenicAreaName(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam("scenicAreaName") String scenicAreaName) {

        Page sessionPage = new Page(page, size);

        LambdaQueryWrapper<Session> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(scenicAreaName != null, Session::getScenicAreaName, scenicAreaName)
                //大于现在时间
                .gt(Session::getSessionDate, LocalDateTime.now().toLocalDate())
                .orderByDesc(Session::getSessionDate)
                .orderByDesc(Session::getSessionTime);

        Page resultPage = sessionService.page(sessionPage, wrapper);

        return new Result("200", resultPage.getRecords(), "查询成功");
    }

    /**
     * 通过场次类型按照日期、时间升序进行查询
     *
     * @param page
     * @param size
     * @param sessionType
     * @return
     */
    @GetMapping("searchBySessionType")
    public Result searchBySessionType(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam("sessionType") String sessionType) {

        Page sessionPage = new Page(page, size);

        LambdaQueryWrapper<Session> wrapper = new LambdaQueryWrapper<>();

        wrapper.like(sessionType != null, Session::getSessionType, sessionType)
                //大于现在时间
                .gt(Session::getSessionDate, LocalDateTime.now().toLocalDate())
                .orderByAsc(Session::getSessionDate)
                .orderByAsc(Session::getSessionTime)
                .like(Session::getSessionType, sessionType);


        Page resultPage = sessionService.page(sessionPage, wrapper);

        return new Result("200", resultPage.getRecords(), "查询成功");
    }
}