package com.mtj.travel.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtj.travel.Mapper.SouvenirMapper;
import com.mtj.travel.Service.SouvenirService;
import com.mtj.travel.entity.Souvenir;
import org.springframework.stereotype.Service;

@Service
public class SouvenirServiceImpl extends ServiceImpl<SouvenirMapper, Souvenir> implements SouvenirService {
}
