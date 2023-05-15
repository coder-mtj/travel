package com.mtj.travel.Controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtj.travel.Dto.TicketDto;
import com.mtj.travel.Service.TicketService;
import com.mtj.travel.entity.Result;
import com.mtj.travel.entity.Session;
import com.mtj.travel.entity.Ticket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RequestMapping(value = "ticket")
@RestController
public class TicketController {
    @Autowired
    private TicketService ticketService;

    /**
     * 通过id获取
     *
     * @param id
     * @return
     */
    @GetMapping(value = "{id}")
    private Result getById(@PathVariable String id) {
        log.info("进入getById");
        Ticket ticket = ticketService.getById(id);
        TicketDto ticketDto = ticketService.getDto(ticket.getSessionId(), ticket);

        return new Result("200", ticketDto, "查询成功");
    }


    /**
     * 传入实体添加
     *
     * @param ticket
     * @return
     */
    @PostMapping
    private Result add(@RequestBody Ticket ticket) {
        log.info("进入/ticket 的add功能");

        boolean save = ticketService.save(ticket);

        if (save) {
            return new Result("200", null, "添加成功");
        } else return new Result("400", null, "实体重复");
    }


    /**
     * 花样查询
     *
     * @param page
     * @param size
     * @param startTime
     * @param endTime
     * @param minPrice
     * @param maxPrice
     * @param type
     * @return
     */
    @GetMapping(value = "search")
    public Result search(@RequestParam(required = false, defaultValue = "1") int page,
                         @RequestParam(required = false, defaultValue = "10") int size,
                         @RequestParam(required = false) String startTime,
                         @RequestParam(required = false) String endTime,
                         @RequestParam(required = false) Double minPrice,
                         @RequestParam(required = false) Double maxPrice,
                         @RequestParam(required = false) String type) {
        log.info("/ticket Get into search...");

        LambdaQueryWrapper<Ticket> ticketLambdaQueryWrapper = new LambdaQueryWrapper<>();

        Page<Ticket> ticketPage = new Page<>(page, size);

        //开始时间
        if (startTime != null && !startTime.isEmpty()) {
            ticketLambdaQueryWrapper.ge(Ticket::getStartTime, startTime);
        } else {
            ticketLambdaQueryWrapper.ge(Ticket::getStartTime, LocalDateTime.now());
        }

        //结束时间
        if (endTime != null && !endTime.isEmpty()) {
            ticketLambdaQueryWrapper.le(Ticket::getEndTime, endTime);
        }

        //最大金额
        if (maxPrice != null) {
            ticketLambdaQueryWrapper.le(Ticket::getPrice, maxPrice);
            log.info("maxPrice:" + maxPrice);
        }

        //最小金额
        if (minPrice != null) {
            ticketLambdaQueryWrapper.ge(Ticket::getPrice, minPrice);
        }

        //类型
        if (type != null && !type.isEmpty()) {
            ticketLambdaQueryWrapper.like(Ticket::getType, type);
        }

        Page<Ticket> res = ticketService.page(ticketPage, ticketLambdaQueryWrapper);

        return new Result("200", res, "查询成功");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "{id}")
    public Result deleteById(@PathVariable String id) {
        log.info("/ticket delete is Loading");

        boolean b = ticketService.removeById(id);
        if (b) {
            return new Result("200", null, "删除成功");
        } else return new Result("404", null, "对应实体不存在");
    }

    /**
     * 传入实体通过id更新
     *
     * @param ticket
     * @return
     */
    @PutMapping
    public Result update(@RequestBody Ticket ticket) {
        log.info("/ticket update is Loading...");

        boolean b = ticketService.updateById(ticket);

        if (b) {
            return new Result("200", null, "修改成功");
        }
        return new Result("404", null, "实体不存在");
    }

    /**
     * 买票
     */
    @GetMapping(value = "{userId}/{ticketId}")
    public Result purchaseTicket(@PathVariable String userId, @PathVariable String ticketId)
    {
        return null;
    }

}
