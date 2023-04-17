package com.mtj.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Souvenir {
    @TableId(type = IdType.ASSIGN_UUID)
    private Long id;  // 纪念品 ID
    private String name;  // 纪念品名称
    private String description;  // 纪念品描述
    private String url;  // 存储url字段
    private String story;  // 纪念品故事
}