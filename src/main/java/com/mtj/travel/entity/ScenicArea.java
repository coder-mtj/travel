package com.mtj.travel.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    // 使用 JacksonTypeHandler 来处理 JSON 数据
    // 文件名之间采用逗号分隔
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
    //景区评分
    private BigDecimal rating;
    @TableField("Related_Facility")
    private String relatedFacility;
    //景区最大容量
    private int capacity;
    @TableLogic(delval = "1")
    @TableField("deleted")
    private boolean deleted;

// 省略构造方法、getters、setters、toString等方法
}