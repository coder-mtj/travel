package com.mtj.travel.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtj.travel.entity.Product;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
