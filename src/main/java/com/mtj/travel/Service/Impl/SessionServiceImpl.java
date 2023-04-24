package com.mtj.travel.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.mtj.travel.Mapper.SessionMapper;
import com.mtj.travel.Service.SessionService;
import com.mtj.travel.entity.Session;
import org.springframework.stereotype.Service;

@Service
public class SessionServiceImpl extends ServiceImpl<SessionMapper, Session> implements SessionService {

}