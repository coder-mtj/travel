package com.mtj.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Repository;


@Repository
@Data
public class Ink {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private String id;
    private String url;
    private String title;
    private int width;
    private int height;

    // getter/setter方法省略
}