package com.mtj.travel.Mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtj.travel.Dto.TicketDto;
import com.mtj.travel.Service.SessionService;
import com.mtj.travel.entity.Session;
import com.mtj.travel.entity.Ticket;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public interface TicketMapper extends BaseMapper<Ticket> {


}
