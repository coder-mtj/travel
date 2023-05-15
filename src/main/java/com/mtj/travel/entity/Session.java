package com.mtj.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

@TableName("session")
@Data
@Component
public class Session implements Serializable {

    private static final long saerialVersionUID = 1L;

    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    @TableField("scenic_area_name")
    private String scenicAreaName;

    @TableField("session_datetime")
    private Date sessionDateTime;

    @TableField("session_location")
    private String sessionLocation;

    @TableField("session_type")
    private String sessionType;

    @TableField("session_duration")
    private Integer sessionDuration;

    @TableField("session_price")
    private Float sessionPrice;

    @TableField("session_description")
    private String sessionDescription;

    @TableField("reserved_count")
    private Integer reservedCount;

    @TableField("max_count")
    private Integer maxCount;

    // getter and setter methods
}
