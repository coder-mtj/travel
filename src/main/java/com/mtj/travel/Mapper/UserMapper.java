package com.mtj.travel.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtj.travel.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {
    // 自定义查询方法
}