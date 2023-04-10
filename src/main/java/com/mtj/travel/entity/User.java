package com.mtj.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    private String username;
    @TableField(select = false)
    private String password;
    private String phoneNumber;
    private String avatarUrl;
    private String identity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private User creatorId;
    private User updaterId;
    @TableLogic(delval = "1")
    private boolean isDeleted;
}