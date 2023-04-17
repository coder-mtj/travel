package com.mtj.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDate;

/**
 * 场次实体类
 */
@Data
public class Session {
    /**
     * 主键ID，使用MyBatis Plus注解 自动生成UUID
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 景区ID
     */
    private String scenicAreaId;

    /**
     * 场次，上午/下午/晚上，使用枚举类型
     */
    private SessionTime session;

    /**
     * 日期
     */
    private LocalDate date;

    /**
     * 价格
     */
    private Double price;

    /**
     * 预约人数
     */
    private Integer bookingCount;
}

/**
 * 枚举类型，表示场次时间
 */
enum SessionTime {
    AM, PM, Night
}
