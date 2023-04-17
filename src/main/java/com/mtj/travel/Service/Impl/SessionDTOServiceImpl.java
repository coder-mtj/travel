package com.mtj.travel.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mtj.travel.DTO.Mapper.SessionDTOMapper;
import com.mtj.travel.DTO.SessionDTO;
import com.mtj.travel.Service.SessionDTOService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * 场次DTO Service 实现类
 */
@Service
public class SessionDTOServiceImpl extends ServiceImpl<SessionDTOMapper, SessionDTO> implements SessionDTOService {
    @Override
    public List<SessionDTO> listByScenicAreaAndDateRange(String scenicAreaId, LocalDate startDate, LocalDate endDate) {
        return baseMapper.listByScenicAreaAndDateRange(scenicAreaId, startDate, endDate);
    }

    @Override
    public SessionDTO getById(String id) {
        return baseMapper.getById(id);
    }
}
