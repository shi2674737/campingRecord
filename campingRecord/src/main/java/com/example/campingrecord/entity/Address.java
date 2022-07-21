package com.example.campingrecord.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * address
 * @author 
 */
@Data
public class Address implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
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
     * 联系电话
     */
    private String phone;

    /**
     * 备注
     */
    private String remark;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;

    private static final long serialVersionUID = 1L;
}