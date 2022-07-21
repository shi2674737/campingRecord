package com.example.campingrecord.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * camping
 * @author 
 */
@Data
public class Camping implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 地点
     */
    private Long addressId;

    /**
     * 0 所有人可见 5 可编辑人可见 9 仅自己可见
     */
    private Integer visibleStatus;

    /**
     * 露营开始时间
     */
    private LocalDateTime campingStartTime;

    /**
     * 露营结束时间
     */
    private LocalDateTime campingEndTime;

    /**
     * 参与人
     */
    private String campingParticipant;

    /**
     * 花费总金额
     */
    private Long totalAmount;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private Long createBy;

    private LocalDateTime createTime;

    private Long updateBy;

    private LocalDateTime updateTime;

    private int deleteFlag;

    private static final long serialVersionUID = 1L;
}