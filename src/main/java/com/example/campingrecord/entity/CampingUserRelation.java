package com.example.campingrecord.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * camping_user_relation
 * @author 
 */
@Data
public class CampingUserRelation implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long campingId;

    private static final long serialVersionUID = 1L;
}