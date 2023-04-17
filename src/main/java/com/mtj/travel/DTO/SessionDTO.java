package com.mtj.travel.DTO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mtj.travel.entity.ScenicArea;
import lombok.Data;

import java.time.LocalDate;

/**
 * 场次DTO，用于接收和返回场次信息
 */
@Data
public class SessionDTO {
    /**
     * 主键ID
     */
    private String id;

    /**
     * 景区信息（编号、名称等）
     */
    private ScenicArea scenicArea;

    /**
     * 场次，上午/下午/晚上
     */
    private SessionTime session;

    /**
     * 日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    /**
     * 价格
     */
    private Double price;

    /**
     * 已预约人数
     */
    @TableField(exist = false)
    private Integer reservedCount;

    /**
     * 可预约人数
     */
    @TableField(exist = false)
    private Integer availableCount;
}
enum SessionTime {
    AM, PM, Night
}