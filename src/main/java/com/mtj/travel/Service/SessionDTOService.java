package com.mtj.travel.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mtj.travel.DTO.SessionDTO;

import java.time.LocalDate;
import java.util.List;

/**
 * 场次DTO Service 接口类
 */
public interface SessionDTOService extends IService<SessionDTO> {
    /**
     * 根据景区ID、时间范围查找场次信息（按时间升序排列）
     *
     * @param scenicAreaId 景区ID
     * @param startDate    开始日期
     * @param endDate      结束日期
     * @return 匹配的场次信息列表
     */
    List<SessionDTO> listByScenicAreaAndDateRange(String scenicAreaId, LocalDate startDate, LocalDate endDate);

    /**
     * 根据场次ID获取场次信息（包含景区信息）
     *
     * @param id 场次ID
     * @return 对应的场次信息
     */
    SessionDTO getById(String id);
}
