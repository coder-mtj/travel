package com.mtj.travel.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtj.travel.Mapper.BrandMapper;
import com.mtj.travel.Service.BrandService;
import com.mtj.travel.entity.Brand;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl extends ServiceImpl<BrandMapper, Brand> implements BrandService {
}
