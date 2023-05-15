package com.mtj.travel.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtj.travel.Dto.TicketDto;
import com.mtj.travel.entity.Ticket;
import org.springframework.stereotype.Service;

@Service
public interface TicketService extends IService<Ticket> {
    abstract TicketDto getDto(String sessionId, Ticket ticket);
}
