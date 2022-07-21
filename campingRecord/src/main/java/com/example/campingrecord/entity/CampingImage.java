package com.example.campingrecord.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * camping_image
 * @author 
 */
@Data
public class CampingImage implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long campingId;

    private String imageUrl;

    private static final long serialVersionUID = 1L;
}