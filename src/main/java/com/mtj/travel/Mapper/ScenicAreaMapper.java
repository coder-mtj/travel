package com.mtj.travel.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtj.travel.entity.ScenicArea;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScenicAreaMapper extends BaseMapper<ScenicArea> {
    // 可以添加自定义方法
}