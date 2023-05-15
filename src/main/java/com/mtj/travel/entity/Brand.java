package com.mtj.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Brand {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String name;
    private String logoUrl;
    private String description;
    private String websiteUrl;
    private String country;
    private int yearFounded;
    private int parentCompanyId;
    @TableLogic(delval = "true")
    private boolean status;
    private int creatorId;
    private LocalDateTime createdTime;
    private Integer modifierId;
    private LocalDateTime modifiedTime;
}