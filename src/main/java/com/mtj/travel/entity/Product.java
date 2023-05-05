package com.mtj.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("product")
public class Product {
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;                   // 产品编号
    private String name;                 // 产品名称
    private String type;                 // 产品类型
    private String description;          // 产品描述
    private BigDecimal price;            // 价格
    private Integer quantity;            // 数量
    private String condition;            // 品相
    private String certificates;         // 证书
    private String brand;                // 品牌
    private String manufacturer;         // 制造商
    private String category;             // 类别
    private BigDecimal weight;           // 重量
    private String dimensions;           // 尺寸
    private String upc;                  // 通用产品代码
    private String sku;                  // 存货单位
    private LocalDateTime createdAt;    // 创建时间
    private LocalDateTime updatedAt;    // 更新时间
}
