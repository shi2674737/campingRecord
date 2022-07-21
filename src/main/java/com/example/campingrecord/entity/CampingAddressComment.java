package com.example.campingrecord.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * camping_address_comment
 * @author 
 */
@Data
public class CampingAddressComment implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private Long addressId;
    private Long campingId;

    /**
     * 评论
     */
    private String comment;

    /**
     * 评分
     */
    private BigDecimal score;

    private LocalDateTime createTime;

    private Long createBy;

    private LocalDateTime updateTime;

    private Long updateBy;

    private static final long serialVersionUID = 1L;
}