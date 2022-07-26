package com.example.campingrecord.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * address
 * @author 
 */
@Data
public class Address implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 详细地址
     */
    private String detailAddress;

    /**
     * 收费标准
     */
    private String cost;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 备注
     */
    private String remark;

    /**
     * 关于使用卡式炉
     */
    private String sycls;

    /**
     * 关于使用碳
     */
    private String carbon;

    /**
     * 关于过夜
     */
    private String overnight;

    /**
     * 优点
     */
    private String advantage;

    /**
     * 缺点
     */
    private String disadvantage;

    private LocalDateTime createTime;

    private Long createBy;

    private LocalDateTime updateTime;

    private Long updateBy;

    private static final long serialVersionUID = 1L;
}