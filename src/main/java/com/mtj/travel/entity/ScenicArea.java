package com.mtj.travel.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Time;

@TableName("Scenic_Area")
@Data
@Component
public class ScenicArea {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    @TableField(condition = SqlCondition.LIKE)
    private String name;
    private String address;
    private String type;
    private String description;
    @TableField(typeHandler = com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler.class)
    // 使用 JacksonTypeHandler 来处理 JSON 数据
    private String picture;
    @TableField("Open_Time")
    private Time openTime;
    @TableField("Close_Time")
    private Time closeTime;
    @TableField("Ticket_Price")
    private BigDecimal ticketPrice;
    @TableField("Traffic_Info")
    private String trafficInfo;
    @TableField("Facility_Info")
    private String facilityInfo;
    @TableField("Contact_Info")
    private String contactInfo;
    @TableField("Tour_Route")
    private String tourRoute;
    @TableField("Restaurant_Info")
    private String restaurantInfo;
    @TableField("Activity_Info")
    private String activityInfo;
    @TableField("Prohibited_Items")
    private String prohibitedItems;
    private BigDecimal rating;
    @TableField("Related_Facility")
    private String relatedFacility;
    private int capacity;

// 省略构造方法、getters、setters、toString等方法
}