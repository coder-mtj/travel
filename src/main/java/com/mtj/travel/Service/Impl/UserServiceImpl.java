package com.mtj.travel.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtj.travel.Mapper.UserMapper;
import com.mtj.travel.Service.UserService;
import com.mtj.travel.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    // 可以自定义UserService接口中未定义的查询方法
}