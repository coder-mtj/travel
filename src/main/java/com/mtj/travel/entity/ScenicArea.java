    package com.mtj.travel.entity;

    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableField;
    import com.baomidou.mybatisplus.annotation.TableId;
    import lombok.Data;

    import java.time.LocalDateTime;

    @Data
    public class ScenicArea {
        @TableId(value = "id", type = IdType.ASSIGN_UUID)
        private String id;
        private String name;
        private String description;
        private String imageSet;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        @TableField("ticket_price")
        private Double ticketPrice;
    }
