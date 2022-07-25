package com.example.campingrecord.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CampingListVo {
    private Long id;
    /**
     * 地点
     */
    private Long addressId;
    /**
     * 地点
     */
    private String addressName;

    /**
     * 0 所有人可见 5 关联人可见 9 仅自己可见
     */
    private Integer visibleStatus;

    /**
     * 露营开始时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime campingStartTime;

    /**
     * 露营结束时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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
     * 参与人数
     */
    private int peopleNum;

    /**
     * 天气
     */
    private String weather;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是不是自己创建的
     */
    private Boolean isOwn;

    /**
     * 创建人id
     */
    private Long createBy;

    private String comment;
    private BigDecimal score;
}
