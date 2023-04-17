package com.mtj.travel.DTO.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mtj.travel.DTO.SessionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * 场次DTO Mapper
 */
@Mapper
public interface SessionDTOMapper extends BaseMapper<SessionDTO> {
    /**
     * 根据景区ID、时间范围查找场次信息
     *
     * @param scenicAreaId 景区ID
     * @param startDate    开始日期
     * @param endDate      结束日期
     * @return 匹配的场次信息列表
     */
    List<SessionDTO> listByScenicAreaAndDateRange(@Param("scenicAreaId") String scenicAreaId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    /**
     * 根据场次ID获取场次信息（包含景区信息）
     *
     * @param id 场次ID
     * @return 对应的场次信息
     */
    SessionDTO getById(@Param("id") String id);
}
