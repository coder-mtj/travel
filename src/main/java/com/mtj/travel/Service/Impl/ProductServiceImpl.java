package com.mtj.travel.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtj.travel.Mapper.ProductMapper;
import com.mtj.travel.Service.ProductService;
import com.mtj.travel.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
}
