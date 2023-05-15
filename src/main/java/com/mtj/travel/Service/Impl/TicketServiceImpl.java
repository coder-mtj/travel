package com.mtj.travel.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtj.travel.Dto.TicketDto;
import com.mtj.travel.Mapper.TicketMapper;
import com.mtj.travel.Service.SessionService;
import com.mtj.travel.Service.TicketService;
import com.mtj.travel.entity.Session;
import com.mtj.travel.entity.Ticket;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl extends ServiceImpl<TicketMapper, Ticket> implements TicketService {
    @Autowired
    public SessionService sessionService;
    public TicketDto getDto(String sessionId, Ticket ticket) {
        Session session = sessionService.getById(sessionId);

        TicketDto ticketDto = new TicketDto(session);
        BeanUtils.copyProperties(ticket, ticketDto);

        return ticketDto;
    }
}